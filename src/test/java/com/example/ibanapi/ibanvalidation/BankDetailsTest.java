package com.example.ibanapi.ibanvalidation;

import org.junit.jupiter.api.Test;

import com.example.ibanapi.ibanvalidation.model.BankDetails;

import static org.junit.jupiter.api.Assertions.*;

public class BankDetailsTest {

    @Test
    public void testDefaultValues() {
        BankDetails bankDetails = new BankDetails();
        assertEquals("Unknown", bankDetails.getBankIdentifier());
        assertEquals("Unknown", bankDetails.getName());
        assertEquals("Unknown", bankDetails.getBic());
        assertEquals("Unknown", bankDetails.getBranch());
        assertEquals("Unknown", bankDetails.getAddress());
        assertEquals("Unknown", bankDetails.getCity());
        assertEquals("Unknown", bankDetails.getZip());
        assertEquals("Unknown", bankDetails.getCountry());
    }

    @Test
    public void testParameterizedConstructor() {
        BankDetails bankDetails = new BankDetails("ID123", "BankName", "BIC123", "BranchName", "Address", "City",
                "12345", "Country");
        assertEquals("ID123", bankDetails.getBankIdentifier());
        assertEquals("BankName", bankDetails.getName());
        assertEquals("BIC123", bankDetails.getBic());
        assertEquals("BranchName", bankDetails.getBranch());
        assertEquals("Address", bankDetails.getAddress());
        assertEquals("City", bankDetails.getCity());
        assertEquals("12345", bankDetails.getZip());
        assertEquals("Country", bankDetails.getCountry());
    }

    @Test
    public void testSettersAndGetters() {
        BankDetails bankDetails = new BankDetails();
        bankDetails.setBankIdentifier("ID123");
        bankDetails.setName("BankName");
        bankDetails.setBic("BIC123");
        bankDetails.setBranch("BranchName");
        bankDetails.setAddress("Address");
        bankDetails.setCity("City");
        bankDetails.setZip("12345");
        bankDetails.setCountry("Country");

        assertEquals("ID123", bankDetails.getBankIdentifier());
        assertEquals("BankName", bankDetails.getName());
        assertEquals("BIC123", bankDetails.getBic());
        assertEquals("BranchName", bankDetails.getBranch());
        assertEquals("Address", bankDetails.getAddress());
        assertEquals("City", bankDetails.getCity());
        assertEquals("12345", bankDetails.getZip());
        assertEquals("Country", bankDetails.getCountry());
    }

    @Test
    public void testAllArgsConstructor() {
        BankDetails bankDetails = new BankDetails("ID123", "BankName", "BIC123", "BranchName", "Address", "City",
                "12345", "Country");
        assertEquals("ID123", bankDetails.getBankIdentifier());
        assertEquals("BankName", bankDetails.getName());
        assertEquals("BIC123", bankDetails.getBic());
        assertEquals("BranchName", bankDetails.getBranch());
        assertEquals("Address", bankDetails.getAddress());
        assertEquals("City", bankDetails.getCity());
        assertEquals("12345", bankDetails.getZip());
        assertEquals("Country", bankDetails.getCountry());
    }
}