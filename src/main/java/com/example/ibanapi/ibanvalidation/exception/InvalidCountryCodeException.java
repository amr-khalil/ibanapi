package com.example.ibanapi.ibanvalidation.exception;

public class InvalidCountryCodeException extends RuntimeException {
    public InvalidCountryCodeException(String message) {
        super(message);
    }
}