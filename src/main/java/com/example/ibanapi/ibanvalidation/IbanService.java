package com.example.ibanapi.ibanvalidation;

import com.example.ibanapi.ibanvalidation.exception.InvalidCountryCodeException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanChecksumException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanEmptyException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanFormatException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanLengthException;
import com.example.ibanapi.ibanvalidation.model.BankDetails;
import com.example.ibanapi.ibanvalidation.model.IbanValidationErrorEnum;
import com.example.ibanapi.ibanvalidation.model.SuccessResponse;
import com.example.ibanapi.ibanvalidation.repository.BankDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
            throw new InvalidIbanEmptyException(IbanValidationErrorEnum.INVALID_LENGTH.getMessage());
        }
        if (normalizedIban.length() < 14) {
            throw new InvalidIbanLengthException(IbanValidationErrorEnum.INVALID_LENGTH.getMessage());
        }
        if (normalizedIban.length() > 34) {
            throw new InvalidIbanLengthException(IbanValidationErrorEnum.INVALID_LENGTH.getMessage());
        }

        // Validate IBAN components
        if (!ibanValidator.isValidIbanFormat(normalizedIban)) {
            throw new InvalidIbanFormatException(IbanValidationErrorEnum.INVALID_FORMAT.getMessage());
        }
        boolean isValidLength = ibanValidator.isValidIbanLength(normalizedIban);
        boolean isValidFormat = ibanValidator.isValidIbanFormat(normalizedIban);
        String countryCode = normalizedIban.substring(0, 2);
        boolean isValidCountryCode = ibanValidator.isValidCountryCode(countryCode);
        boolean isValidChecksum = ibanValidator.isValidIbanChecksum(normalizedIban);
        boolean isValid = isValidLength && isValidFormat && isValidCountryCode && isValidChecksum;

        if (!isValidCountryCode) {
            throw new InvalidCountryCodeException(IbanValidationErrorEnum.INVALID_COUNTRY_CODE.getMessage());
        }
        if (!isValidChecksum) {
            throw new InvalidIbanChecksumException(IbanValidationErrorEnum.INVALID_CHECKSUM.getMessage());
        }

        if (!isValid) {
            logger.warn("Invalid IBAN detected: {}", iban);
        }

        BankDetails bankDetails = new BankDetails("Unknown", "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
                "Unknown", "Unknown");
        if (isValid) {
            String bankIdentifier = normalizedIban.substring(0, 8);
            logger.debug("Retrieving bank details for bank identifier: {}", bankIdentifier);

            bankDetails = bankDetailsRepository.findById(bankIdentifier).orElse(new BankDetails("Unknown", "Unknown",
                    "Unknown", "Unknown", "Unknown", "Unknown", "Unknown", "Unknown"));

            if (!"Unknown".equals(bankDetails.getBankIdentifier())) {
                logger.debug("Bank details retrieved: {}", bankDetails);
            } else {
                logger.warn("No bank details found for bank identifier: {}", bankIdentifier);
            }
        }

        SuccessResponse response = new SuccessResponse();
        response.setIban(iban);
        response.setValid(isValid);
        response.setValidLength(isValidLength);
        response.setValidFormat(isValidFormat);
        response.setValidCountryCode(isValidCountryCode);
        response.setValidChecksum(isValidChecksum);
        response.setBankDetails(bankDetails);

        logger.debug("IBAN validation response created: {}", response);
        return response;
    }
}