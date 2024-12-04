package com.example.ibanapi.ibanvalidation;

import com.example.ibanapi.ibanvalidation.exception.InvalidIbanEmptyException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanFormatException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanLengthException;
import com.example.ibanapi.ibanvalidation.model.BankDetails;
import com.example.ibanapi.ibanvalidation.model.SuccessResponse;
import com.example.ibanapi.ibanvalidation.repository.BankDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IbanService {

    private static final Logger logger = LoggerFactory.getLogger(IbanService.class);

    private final IbanValidator ibanValidator;
    private final BankDetailsRepository bankDetailsRepository;

    // Constructor-based dependency injection
    public IbanService(IbanValidator ibanValidator, BankDetailsRepository bankDetailsRepository) {
        this.ibanValidator = ibanValidator;
        this.bankDetailsRepository = bankDetailsRepository;
    }

    @Cacheable(value = "ibanValidation", key = "#iban")
    public SuccessResponse validateIban(String iban) {
        // Normalize IBAN
        String normalizedIban = ibanValidator.normalizeIban(iban);
        logger.debug("Normalized IBAN: {}", normalizedIban);

        // Ensure normalized IBAN has minimum length
        if (normalizedIban.isEmpty()) {
            throw new InvalidIbanEmptyException("IBAN value is empty.");
        }
        if (normalizedIban.length() < 14) {
            throw new InvalidIbanLengthException("IBAN is too short, it should be at least 14 characters long.");
        }
        if (normalizedIban.length() > 34) {
            throw new InvalidIbanLengthException("IBAN is too long, it should be at most 34 characters long.");
        }

        // Validate IBAN components
        if (!ibanValidator.isValidIbanFormat(normalizedIban)) {
            throw new InvalidIbanFormatException("IBAN format is invalid.");
        }
        boolean isValidLength = ibanValidator.isValidIbanLength(normalizedIban);
        boolean isValidFormat = ibanValidator.isValidIbanFormat(normalizedIban);
        String countryCode = normalizedIban.substring(0, 2);
        boolean isValidCountryCode = ibanValidator.isValidCountryCode(countryCode);
        boolean isValidChecksum = ibanValidator.isValidIbanChecksum(normalizedIban);
        boolean isValid = isValidLength && isValidFormat && isValidCountryCode && isValidChecksum;

        if (!isValid) {
            logger.warn("Invalid IBAN detected: {}", iban);
        }

        Map<String, String> details = null;
        if (isValid) {
            // Ensure normalized IBAN has sufficient length for bank identifier extraction
            if (normalizedIban.length() >= 12) {
                String bankIdentifier = normalizedIban.substring(4, 12); // Adjusted indices for bank code
                logger.debug("Retrieving bank details for bank identifier: {}", bankIdentifier);

                BankDetails bankDetails = bankDetailsRepository.findById(bankIdentifier).orElse(null);

                if (bankDetails != null) {
                    details = new HashMap<>();
                    details.put("name", bankDetails.getName());
                    details.put("bic", bankDetails.getBic());
                    details.put("branch", bankDetails.getBranch());
                    details.put("address", bankDetails.getAddress());
                    details.put("city", bankDetails.getCity());
                    details.put("zip", bankDetails.getZip());
                    details.put("country", bankDetails.getCountry());
                    logger.debug("Bank details retrieved: {}", details);
                } else {
                    logger.warn("No bank details found for bank identifier: {}", bankIdentifier);
                }
            } else {
                throw new InvalidIbanException("IBAN is too short for bank details extraction.");
            }
        }

        SuccessResponse response = new SuccessResponse();
        response.setIban(iban);
        response.setValid(isValid);
        response.setValidLength(isValidLength);
        response.setValidFormat(isValidFormat);
        response.setValidCountryCode(isValidCountryCode);
        response.setValidChecksum(isValidChecksum);
        response.setDetails(details);

        logger.debug("IBAN validation response created: {}", response);
        return response;
    }
}