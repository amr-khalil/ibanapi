package com.example.ibanapi.ibanvalidation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IbanValidationRequest {
    private String iban;

}
