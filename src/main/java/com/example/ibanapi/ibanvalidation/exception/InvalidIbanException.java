package com.example.ibanapi.ibanvalidation.exception;

public class InvalidIbanException extends RuntimeException {
    public InvalidIbanException(String message) {
        super(message);
    }
}