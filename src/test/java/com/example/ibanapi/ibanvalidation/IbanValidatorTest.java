package com.example.ibanapi.ibanvalidation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IbanValidatorTest {

    private final IbanValidator ibanValidator = new IbanValidator();

    @Test
    public void testNormalizeIban() {
        assertEquals("DE89370400440532013000", ibanValidator.normalizeIban("DE89 3704 0044 0532 0130 00"));
        assertEquals("GB82WEST12345698765432", ibanValidator.normalizeIban("GB82-WEST-1234-5698-7654-32"));
    }

    @Test
    public void testIsValidIbanLength() {
        assertTrue(ibanValidator.isValidIbanLength("DE89370400440532013000"));
        assertFalse(ibanValidator.isValidIbanLength("DE8937040044053201300"));
    }

    @Test
    public void testIsValidIbanFormat() {
        assertTrue(ibanValidator.isValidIbanFormat("DE89370400440532013000"));
        assertFalse(ibanValidator.isValidIbanFormat("DE89 3704 0044 0532 0130 00"));
    }

    @Test
    public void testIsValidIbanChecksum() {
        assertTrue(ibanValidator.isValidIbanChecksum("DE89370400440532013000"));
        assertFalse(ibanValidator.isValidIbanChecksum("DE89370400440532013001"));
    }

    @Test
    public void testIsValidCountryCode() {
        assertTrue(ibanValidator.isValidCountryCode("DE"));
        assertFalse(ibanValidator.isValidCountryCode("XX"));
    }

    @Test
    public void testGetIbanChecksum() {
        assertEquals(1, ibanValidator.getIbanChecksum("DE89370400440532013000"));
        assertNotEquals(1, ibanValidator.getIbanChecksum("DE89370400440532013001"));
    }
}
