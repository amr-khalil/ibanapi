package com.example.ibanapi.ibanvalidation.exception;

public class InvalidIbanChecksumException extends RuntimeException {
    public InvalidIbanChecksumException(String message) {
        super(message);
    }
}
