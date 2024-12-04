package com.example.ibanapi.ibanvalidation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IbanValidator {
    // Country-specific IBAN lengths (can be extended)
    private static final Map<String, Integer> IBAN_LENGTHS = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(IbanValidator.class);

    static {
        IBAN_LENGTHS.put("AL", 28);
        IBAN_LENGTHS.put("AD", 24);
        IBAN_LENGTHS.put("AT", 20);
        IBAN_LENGTHS.put("AZ", 28);
        IBAN_LENGTHS.put("BH", 22);
        IBAN_LENGTHS.put("BE", 16);
        IBAN_LENGTHS.put("BA", 20);
        IBAN_LENGTHS.put("BR", 29);
        IBAN_LENGTHS.put("BG", 22);
        IBAN_LENGTHS.put("CR", 22);
        IBAN_LENGTHS.put("HR", 21);
        IBAN_LENGTHS.put("CY", 28);
        IBAN_LENGTHS.put("CZ", 24);
        IBAN_LENGTHS.put("DK", 18);
        IBAN_LENGTHS.put("DO", 28);
        IBAN_LENGTHS.put("EE", 20);
        IBAN_LENGTHS.put("FO", 18);
        IBAN_LENGTHS.put("FI", 18);
        IBAN_LENGTHS.put("FR", 27);
        IBAN_LENGTHS.put("GE", 22);
        IBAN_LENGTHS.put("DE", 22);
        IBAN_LENGTHS.put("GI", 23);
        IBAN_LENGTHS.put("GR", 27);
        IBAN_LENGTHS.put("GL", 18);
        IBAN_LENGTHS.put("GT", 28);
        IBAN_LENGTHS.put("HU", 28);
        IBAN_LENGTHS.put("IS", 26);
        IBAN_LENGTHS.put("IE", 22);
        IBAN_LENGTHS.put("IL", 23);
        IBAN_LENGTHS.put("IT", 27);
        IBAN_LENGTHS.put("JO", 30);
        IBAN_LENGTHS.put("KZ", 20);
        IBAN_LENGTHS.put("KW", 30);
        IBAN_LENGTHS.put("LV", 21);
        IBAN_LENGTHS.put("LB", 28);
        IBAN_LENGTHS.put("LI", 21);
        IBAN_LENGTHS.put("LT", 20);
        IBAN_LENGTHS.put("LU", 20);
        IBAN_LENGTHS.put("MT", 31);
        IBAN_LENGTHS.put("MR", 27);
        IBAN_LENGTHS.put("MU", 30);
        IBAN_LENGTHS.put("MC", 27);
        IBAN_LENGTHS.put("MD", 24);
        IBAN_LENGTHS.put("ME", 22);
        IBAN_LENGTHS.put("NL", 18);
        IBAN_LENGTHS.put("NO", 15);
        IBAN_LENGTHS.put("PK", 24);
        IBAN_LENGTHS.put("PS", 29);
        IBAN_LENGTHS.put("PL", 28);
        IBAN_LENGTHS.put("PT", 25);
        IBAN_LENGTHS.put("QA", 29);
        IBAN_LENGTHS.put("RO", 24);
        IBAN_LENGTHS.put("SM", 27);
        IBAN_LENGTHS.put("SA", 24);
        IBAN_LENGTHS.put("RS", 22);
        IBAN_LENGTHS.put("SK", 24);
        IBAN_LENGTHS.put("SI", 19);
        IBAN_LENGTHS.put("ES", 24);
        IBAN_LENGTHS.put("SE", 24);
        IBAN_LENGTHS.put("CH", 21);
        IBAN_LENGTHS.put("TN", 24);
        IBAN_LENGTHS.put("TR", 26);
        IBAN_LENGTHS.put("UA", 29);
        IBAN_LENGTHS.put("AE", 23);
        IBAN_LENGTHS.put("GB", 22);
        IBAN_LENGTHS.put("VG", 24);
    }

    public String normalizeIban(String iban) {
        try {
            return iban.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        } catch (Exception e) {
            logger.error("Failed to normalize IBAN {}: {}", iban, e.getMessage());
            return "";
        }
    }

    public boolean isValidIbanLength(String iban) {
        try {
            String countryCode = iban.substring(0, 2);
            return IBAN_LENGTHS.containsKey(countryCode) && iban.length() == IBAN_LENGTHS.get(countryCode);
        } catch (Exception e) {
            logger.error("Failed to validate IBAN {} length: {}", iban, e.getMessage());
            return false;
        }
    }

    public boolean isValidIbanFormat(String iban) {
        try {
            return iban.matches("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$");
        } catch (Exception e) {
            logger.error("Failed to validate IBAN {} format: {}", iban, e.getMessage());
            return false;
        }
    }

    public boolean isValidIbanChecksum(String iban) {
        try {
            return getIbanChecksum(iban) == 1;
        } catch (Exception e) {
            logger.error("Failed to validate IBAN {} checksum: {}", iban, e.getMessage());
            return false;
        }
    }

    public boolean isValidCountryCode(String countryCode) {
        try {
            return IBAN_LENGTHS.containsKey(countryCode);
        } catch (Exception e) {
            logger.error("Failed to validate IBAN country code {}: {}", countryCode, e.getMessage());
            return false;
        }
    }

    public int getIbanChecksum(String iban) {
        try {
            String rearrangedIban = iban.substring(4) + iban.substring(0, 4);
            StringBuilder numericIban = new StringBuilder();
            for (char ch : rearrangedIban.toCharArray()) {
                if (Character.isLetter(ch)) {
                    numericIban.append((int) ch - 55);
                } else {
                    numericIban.append(ch);
                }
            }
            return new java.math.BigInteger(numericIban.toString()).mod(java.math.BigInteger.valueOf(97)).intValue();
        } catch (Exception e) {
            logger.error("Failed to calculate IBAN {} checksum: {}", iban, e.getMessage());
            return -1;
        }
    }

}
