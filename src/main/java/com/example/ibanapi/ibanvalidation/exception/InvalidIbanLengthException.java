package com.example.ibanapi.ibanvalidation.exception;

public class InvalidIbanLengthException extends RuntimeException {
    public InvalidIbanLengthException(String message) {
        super(message);
    }
}
