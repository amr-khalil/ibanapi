package com.example.ibanapi.ibanvalidation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String status = "success";
    private String iban;
    private boolean valid;
    private boolean validLength;
    private boolean validFormat;
    private boolean validCountryCode;
    private boolean validChecksum;
    private Map<String, String> details;
}