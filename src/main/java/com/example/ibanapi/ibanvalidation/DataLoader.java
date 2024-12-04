package com.example.ibanapi.ibanvalidation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ibanapi.ibanvalidation.model.BankDetails;
import com.example.ibanapi.ibanvalidation.repository.BankDetailsRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(BankDetailsRepository repository) {
        return args -> {
            repository.save(new BankDetails(
                    "DE893704",
                    "Deutsche Bank",
                    "DEUTDEFF",
                    "FFM-Ost",
                    "Taunusanlage 12",
                    "Frankfurt am Main",
                    "60325",
                    "Germany"));
            repository.save(new BankDetails(
                    "GB82WEST",
                    "Westminster Bank",
                    "WESTGB22",
                    "London-West",
                    "10 South Colonnade",
                    "London",
                    "E14 4PU",
                    "United Kingdom"));
            repository.save(new BankDetails(
                    "FR763000",
                    "Société Générale",
                    "SOGEFRPP",
                    "Paris-North",
                    "29 Boulevard Haussmann",
                    "Paris",
                    "75009",
                    "France"));
        };
    }
}