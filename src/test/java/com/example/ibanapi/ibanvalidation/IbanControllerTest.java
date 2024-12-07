package com.example.ibanapi.ibanvalidation;

import com.example.ibanapi.ibanvalidation.exception.InvalidCountryCodeException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanChecksumException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanEmptyException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanFormatException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanLengthException;
import com.example.ibanapi.ibanvalidation.model.BankDetails;
import com.example.ibanapi.ibanvalidation.model.IbanValidationErrorEnum;
import com.example.ibanapi.ibanvalidation.model.SuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IbanControllerTest {

    @Mock
    private IbanService ibanService;

    @InjectMocks
    private IbanController ibanController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ibanController).build();
    }

    @Test
    void testValidateIban_ValidIbanWithBankDetails() throws Exception {
        // Arrange
        BankDetails bankDetails = new BankDetails("DE445001", "Test Bank", "DE12345", "Test Branch", "Test Address",
                "Test City", "12345", "Germany");
        SuccessResponse response = new SuccessResponse();
        response.setIban("DE44500105175407324931");
        response.setValid(true);
        response.setBankDetails(bankDetails);

        when(ibanService.validateIban(any(String.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/iban/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"iban\": \"DE44500105175407324931\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"status\":\"success\",\"iban\":\"DE44500105175407324931\",\"valid\":true,\"bankDetails\":{\"bankIdentifier\":\"DE445001\",\"name\":\"Test Bank\",\"bic\":\"DE12345\",\"branch\":\"Test Branch\",\"address\":\"Test Address\",\"city\":\"Test City\",\"zip\":\"12345\",\"country\":\"Germany\"}}"));
    }

    @Test
    void testGetStatus() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/iban/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Status OK"));
    }

    @Test
    void testValidateIban_InvalidIbanEmpty() throws Exception {
        // Arrange
        when(ibanService.validateIban(any(String.class)))
                .thenThrow(new InvalidIbanEmptyException(IbanValidationErrorEnum.INVALID_LENGTH.getMessage()));

        // Act & Assert
        mockMvc.perform(post("/api/iban/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"iban\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"status\":\"error\",\"errorType\":\"" + IbanValidationErrorEnum.INVALID_LENGTH.name()
                                + "\",\"message\":\"" + IbanValidationErrorEnum.INVALID_LENGTH.getMessage() + "\"}"));
    }

    @Test
    void testValidateIban_InvalidIbanLength() throws Exception {
        // Arrange
        when(ibanService.validateIban(any(String.class)))
                .thenThrow(new InvalidIbanLengthException(IbanValidationErrorEnum.INVALID_LENGTH.getMessage()));

        // Act & Assert
        mockMvc.perform(post("/api/iban/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"iban\": \"DE12\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"status\":\"error\",\"errorType\":\"" + IbanValidationErrorEnum.INVALID_LENGTH.name()
                                + "\",\"message\":\"" + IbanValidationErrorEnum.INVALID_LENGTH.getMessage() + "\"}"));
    }

    @Test
    void testValidateIban_InvalidIbanFormat() throws Exception {
        // Arrange
        when(ibanService.validateIban(any(String.class)))
                .thenThrow(new InvalidIbanFormatException(IbanValidationErrorEnum.INVALID_FORMAT.getMessage()));

        // Act & Assert
        mockMvc.perform(post("/api/iban/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"iban\": \"INVALIDIBAN\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"status\":\"error\",\"errorType\":\"" + IbanValidationErrorEnum.INVALID_FORMAT.name()
                                + "\",\"message\":\"" + IbanValidationErrorEnum.INVALID_FORMAT.getMessage() + "\"}"));
    }

    @Test
    void testValidateIban_InvalidCountryCode() throws Exception {
        // Arrange
        when(ibanService.validateIban(any(String.class)))
                .thenThrow(new InvalidCountryCodeException(IbanValidationErrorEnum.INVALID_COUNTRY_CODE.getMessage()));

        // Act & Assert
        mockMvc.perform(post("/api/iban/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"iban\": \"XX89370400440532013000\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"status\":\"error\",\"errorType\":\"" + IbanValidationErrorEnum.INVALID_COUNTRY_CODE.name()
                                + "\",\"message\":\"" + IbanValidationErrorEnum.INVALID_COUNTRY_CODE.getMessage()
                                + "\"}"));
    }

    @Test
    void testValidateIban_InvalidIbanChecksum() throws Exception {
        // Arrange
        when(ibanService.validateIban(any(String.class)))
                .thenThrow(new InvalidIbanChecksumException(IbanValidationErrorEnum.INVALID_CHECKSUM.getMessage()));

        // Act & Assert
        mockMvc.perform(post("/api/iban/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"iban\": \"DE89370400440532013001\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{\"status\":\"error\",\"errorType\":\"" + IbanValidationErrorEnum.INVALID_CHECKSUM.name()
                                + "\",\"message\":\"" + IbanValidationErrorEnum.INVALID_CHECKSUM.getMessage() + "\"}"));
    }

}