package com.example.ibanapi.ibanvalidation;

import com.example.ibanapi.ibanvalidation.exception.InvalidCountryCodeException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanChecksumException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanEmptyException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanFormatException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanLengthException;
import com.example.ibanapi.ibanvalidation.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidIbanEmptyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanEmptyException(InvalidIbanEmptyException ex,
            WebRequest request) {
        logger.warn("Invalid IBAN Empty exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Invalid IBAN Empty", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIbanLengthException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanLengthException(InvalidIbanLengthException ex,
            WebRequest request) {
        logger.warn("Invalid IBAN length exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Invalid IBAN Length", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIbanFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanFormatException(InvalidIbanFormatException ex,
            WebRequest request) {
        logger.warn("Invalid IBAN format exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Invalid IBAN Format", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIbanChecksumException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanChecksumException(InvalidIbanChecksumException ex,
            WebRequest request) {
        logger.warn("Invalid IBAN checksum exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Invalid IBAN Checksum", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCountryCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCountryCodeException(InvalidCountryCodeException ex,
            WebRequest request) {
        logger.warn("Invalid country code exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Invalid Country Code", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.warn("Unexpected error: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", "An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}