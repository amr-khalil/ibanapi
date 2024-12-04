package com.example.ibanapi.ibanvalidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private final String status = "error";
    private String errorType;
    private String message;

}