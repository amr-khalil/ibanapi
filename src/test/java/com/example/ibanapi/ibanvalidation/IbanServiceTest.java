package com.example.ibanapi.ibanvalidation;

import com.example.ibanapi.ibanvalidation.exception.InvalidIbanEmptyException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanFormatException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanLengthException;
import com.example.ibanapi.ibanvalidation.model.BankDetails;
import com.example.ibanapi.ibanvalidation.model.SuccessResponse;
import com.example.ibanapi.ibanvalidation.repository.BankDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IbanServiceTest {

    @Mock
    private IbanValidator ibanValidator;

    @Mock
    private BankDetailsRepository bankDetailsRepository;

    @InjectMocks
    private IbanService ibanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateIban_ValidIbanWithBankDetails() {
        // Arrange
        String iban = "DE-44 5001 0517 5407 3249 31";
        String normalizedIban = "DE44500105175407324931";
        String bankIdentifier = "DE445001";

        BankDetails bankDetails = new BankDetails();
        bankDetails.setBankIdentifier(bankIdentifier);
        bankDetails.setName("Deutsch Bank");
        bankDetails.setBic("DE12345");
        bankDetails.setBranch("Frankfurt Ost");
        bankDetails.setAddress("Test Address");
        bankDetails.setCity("Frankfurt");
        bankDetails.setZip("12345");
        bankDetails.setCountry("Germany");

        when(ibanValidator.normalizeIban(iban)).thenReturn(normalizedIban);
        when(ibanValidator.isValidIbanLength(normalizedIban)).thenReturn(true);
        when(ibanValidator.isValidIbanFormat(normalizedIban)).thenReturn(true);
        when(ibanValidator.isValidCountryCode("DE")).thenReturn(true);
        when(ibanValidator.isValidIbanChecksum(normalizedIban)).thenReturn(true);
        when(bankDetailsRepository.findById(bankIdentifier)).thenReturn(Optional.of(bankDetails));

        // Act
        SuccessResponse response = ibanService.validateIban(iban);

        // Assert
        assertTrue(response.isValid());
        assertEquals(bankDetails, response.getBankDetails());
        System.out.println(response.getBankDetails());
        assertEquals(iban, response.getIban());

        verify(bankDetailsRepository, times(1)).findById(bankIdentifier);
    }

    @Test
    void testValidateIban_InvalidLength() {
        // Arrange
        String iban = "DE-1234 166 76 11 1 DE";
        String normalizedIban = "DE123416676111DE";

        when(ibanValidator.normalizeIban(iban)).thenReturn(normalizedIban);
        when(ibanValidator.isValidIbanFormat(normalizedIban)).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidIbanFormatException.class, () -> ibanService.validateIban(iban));

        verifyNoInteractions(bankDetailsRepository);
    }

    @Test
    void testValidateIban_EmptyIban() {
        // Arrange
        String iban = "";

        when(ibanValidator.normalizeIban(iban)).thenReturn(iban);

        // Act & Assert
        assertThrows(InvalidIbanEmptyException.class, () -> ibanService.validateIban(iban));

        verifyNoInteractions(bankDetailsRepository);
    }

    @Test
    void testValidateIban_ShortIban() {
        // Arrange
        String iban = "DE12";
        String normalizedIban = "DE12";

        when(ibanValidator.normalizeIban(iban)).thenReturn(normalizedIban);

        // Act & Assert
        assertThrows(InvalidIbanLengthException.class, () -> ibanService.validateIban(iban));

        verifyNoInteractions(bankDetailsRepository);
    }

    @Test
    void testValidateIban_NoBankDetailsFound() {
        // Arrange
        String iban = "DE44500105175407324931";
        String normalizedIban = "DE44500105175407324931";
        String bankIdentifier = "DE445001";

        when(ibanValidator.normalizeIban(iban)).thenReturn(normalizedIban);
        when(ibanValidator.isValidIbanLength(normalizedIban)).thenReturn(true);
        when(ibanValidator.isValidIbanFormat(normalizedIban)).thenReturn(true);
        when(ibanValidator.isValidCountryCode("DE")).thenReturn(true);
        when(ibanValidator.isValidIbanChecksum(normalizedIban)).thenReturn(true);
        when(bankDetailsRepository.findById(bankIdentifier)).thenReturn(Optional.empty());

        // Act
        SuccessResponse response = ibanService.validateIban(iban);

        // Assert
        assertTrue(response.isValid());
        assertNotNull(response.getBankDetails());
        assertEquals("Unknown", response.getBankDetails().getBankIdentifier());
        assertEquals(iban, response.getIban());

        verify(bankDetailsRepository, times(1)).findById(bankIdentifier);
    }
}