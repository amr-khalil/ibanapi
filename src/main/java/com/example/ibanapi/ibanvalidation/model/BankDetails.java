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
    private String bankIdentifier = "Unknown";

    @Column(nullable = false, unique = true)
    private String name = "Unknown";

    @Column(nullable = false)
    private String bic = "Unknown";

    private String branch = "Unknown";

    private String address = "Unknown";

    private String city = "Unknown";

    private String zip = "Unknown";

    private String country = "Unknown";
}