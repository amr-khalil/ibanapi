package com.example.ibanapi.ibanvalidation.util;

public enum IbanValidationError {
    INVALID_LENGTH("Invalid IBAN length: The IBAN does not meet the required length criteria."),
    INVALID_FORMAT("Invalid IBAN format: The IBAN contains invalid characters or format."),
    INVALID_COUNTRY_CODE("Invalid country code: The IBAN contains an unsupported country code."),
    INVALID_CHECKSUM("Invalid IBAN checksum: The IBAN failed the checksum validation.");

    private final String message;

    IbanValidationError(String message) {
        this.message = message;
    }

    public String getMessage(String... args) {
        return message;
    }
}
