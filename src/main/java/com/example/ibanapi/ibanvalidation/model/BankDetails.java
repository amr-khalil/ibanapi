package com.example.ibanapi.ibanvalidation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    @Id
    @Column(name = "bank_identifier", nullable = false, unique = true)
    private String bankIdentifier;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String bic;

    private String branch;

    private String address;

    private String city;

    private String zip;

    private String country;
}