package com.example.ibanapi.ibanvalidation.exception;

public class InvalidIbanEmptyException extends RuntimeException {
    public InvalidIbanEmptyException(String message) {
        super(message);
    }
}
