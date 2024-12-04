package com.example.ibanapi.ibanvalidation.exception;

public class InvalidIbanFormatException extends RuntimeException {
    public InvalidIbanFormatException(String message) {
        super(message);
    }
}
