package com.example.ibanapi.config;

import com.example.ibanapi.ibanvalidation.model.BankDetails;
import com.example.ibanapi.ibanvalidation.repository.BankDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                        repository.save(new BankDetails(
                                        "NL91ABNA",
                                        "ABN AMRO",
                                        "ABNANL2A",
                                        "Amsterdam",
                                        "Gustav Mahlerlaan 10",
                                        "Amsterdam",
                                        "1082 PP",
                                        "Netherlands"));
                        repository.save(new BankDetails(
                                        "ES912100",
                                        "Banco Santander",
                                        "BSCHESMM",
                                        "Madrid",
                                        "Paseo de Pereda 9-12",
                                        "Madrid",
                                        "39004",
                                        "Spain"));
                        repository.save(new BankDetails(
                                        "IT60X054",
                                        "Intesa Sanpaolo",
                                        "BCITITMM",
                                        "Turin",
                                        "Piazza San Carlo 156",
                                        "Turin",
                                        "10121",
                                        "Italy"));
                        repository.save(new BankDetails(
                                        "BE685390",
                                        "ING Belgium",
                                        "BBRUBEBB",
                                        "Brussels",
                                        "Avenue Marnix 24",
                                        "Brussels",
                                        "1000",
                                        "Belgium"));
                        repository.save(new BankDetails(
                                        "CH930076",
                                        "UBS Switzerland",
                                        "UBSWCHZH80A",
                                        "Zurich",
                                        "Bahnhofstrasse 45",
                                        "Zurich",
                                        "8001",
                                        "Switzerland"));
                        repository.save(new BankDetails(
                                        "SE455000",
                                        "Swedbank",
                                        "SWEDSESS",
                                        "Stockholm",
                                        "Landsvägen 40",
                                        "Stockholm",
                                        "172 63",
                                        "Sweden"));
                        repository.save(new BankDetails(
                                        "DK500040",
                                        "Danske Bank",
                                        "DABADKKK",
                                        "Copenhagen",
                                        "Holmens Kanal 2-12",
                                        "Copenhagen",
                                        "1092",
                                        "Denmark"));
                };
        }
}