package com.example.ibanapi.ibanvalidation;

import com.example.ibanapi.ibanvalidation.exception.InvalidCountryCodeException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanChecksumException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanEmptyException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanFormatException;
import com.example.ibanapi.ibanvalidation.exception.InvalidIbanLengthException;
import com.example.ibanapi.ibanvalidation.model.ErrorResponse;
import com.example.ibanapi.ibanvalidation.model.IbanValidationErrorEnum;
import com.example.ibanapi.ibanvalidation.model.IbanValidationRequest;
import com.example.ibanapi.ibanvalidation.model.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestController
@RequestMapping("/api/iban")
public class IbanController {

    private static final Logger logger = LoggerFactory.getLogger(IbanController.class);

    private final IbanService ibanService;

    public IbanController(IbanService ibanService) {
        this.ibanService = ibanService;
    }

    @Operation(summary = "Validate IBAN", description = "Validates an IBAN and returns the validation result along with bank details if available.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "IBAN validation request", required = true, content = @Content(schema = @Schema(implementation = IbanValidationRequest.class), examples = {
            @ExampleObject(name = "Example IBAN DE", value = "{\"iban\": \"DE89370400440532013000\"}"),
            @ExampleObject(name = "Example IBAN GB", value = "{\"iban\": \"GB82WEST12345698765432\"}"),
            @ExampleObject(name = "Example IBAN FR", value = "{\"iban\": \"FR7630006000011234567890189\"}")
    })), responses = {
            @ApiResponse(responseCode = "200", description = "IBAN validation response", content = @Content(schema = @Schema(implementation = SuccessResponse.class), examples = {
                    @ExampleObject(name = "Example Response DE", value = "{\"status\":\"success\",\"iban\":\"DE89370400440532013000\",\"valid\":true,\"validLength\":true,\"validFormat\":true,\"validCountryCode\":true,\"validChecksum\":true,\"bankDetails\":{\"bankIdentifier\":\"DE893704\",\"name\":\"Deutsche Bank\",\"bic\":\"DEUTDEFF\",\"branch\":\"FFM-Ost\",\"address\":\"Taunusanlage 12\",\"city\":\"Frankfurt am Main\",\"zip\":\"60325\",\"country\":\"Germany\"}}"),
                    @ExampleObject(name = "Example Response GB", value = "{\"status\":\"success\",\"iban\":\"GB82WEST12345698765432\",\"valid\":true,\"validLength\":true,\"validFormat\":true,\"validCountryCode\":true,\"validChecksum\":true,\"bankDetails\":{\"bankIdentifier\":\"GB82WEST\",\"name\":\"Westminster Bank\",\"bic\":\"WESTGB22\",\"branch\":\"London-West\",\"address\":\"10 South Colonnade\",\"city\":\"London\",\"zip\":\"E14 4PU\",\"country\":\"United Kingdom\"}}"),
                    @ExampleObject(name = "Example Response FR", value = "{\"status\":\"success\",\"iban\":\"FR7630006000011234567890189\",\"valid\":true,\"validLength\":true,\"validFormat\":true,\"validCountryCode\":true,\"validChecksum\":true,\"bankDetails\":{\"bankIdentifier\":\"FR763000\",\"name\":\"Société Générale\",\"bic\":\"SOGEFRPP\",\"branch\":\"Paris-North\",\"address\":\"29 Boulevard Haussmann\",\"city\":\"Paris\",\"zip\":\"75009\",\"country\":\"France\"}}")
            })),
            @ApiResponse(responseCode = "400", description = "Invalid IBAN Empty", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Invalid IBAN Empty", value = "{\"status\":\"error\",\"errorType\":\"INVALID_LENGTH\",\"message\":\"The length of the IBAN is invalid.\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid IBAN Length", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Invalid IBAN Length", value = "{\"status\":\"error\",\"errorType\":\"INVALID_LENGTH\",\"message\":\"The length of the IBAN is invalid.\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid IBAN Format", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Invalid IBAN Format", value = "{\"status\":\"error\",\"errorType\":\"INVALID_FORMAT\",\"message\":\"The format of the IBAN is invalid.\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid Country Code", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Invalid Country Code", value = "{\"status\":\"error\",\"errorType\":\"INVALID_COUNTRY_CODE\",\"message\":\"The country code of the IBAN is invalid.\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid IBAN Checksum", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Invalid IBAN Checksum", value = "{\"status\":\"error\",\"errorType\":\"INVALID_CHECKSUM\",\"message\":\"The checksum of the IBAN is invalid.\"}"))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Not Found", value = "{\"status\":\"error\",\"errorType\":\"Not Found\",\"message\":\"The requested resource was not found.\"}")))
    })
    @PostMapping("/validate")
    public ResponseEntity<SuccessResponse> validateIban(@RequestBody IbanValidationRequest request) {
        logger.debug("Received IBAN validation request: {}", request);
        SuccessResponse response = ibanService.validateIban(request.getIban());
        logger.debug("Returning IBAN validation response: {}", response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Status", description = "Returns a simple status message indicating that the service is running.", responses = {
            @ApiResponse(responseCode = "200", description = "Status OK", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(name = "Example Status", value = "Status OK")))
    })
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Status OK");
    }

    @ExceptionHandler(InvalidIbanEmptyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanEmptyException(InvalidIbanEmptyException ex) {
        logger.warn("Invalid IBAN Empty exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(IbanValidationErrorEnum.INVALID_LENGTH.name(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIbanLengthException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanLengthException(InvalidIbanLengthException ex) {
        logger.warn("Invalid IBAN length exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(IbanValidationErrorEnum.INVALID_LENGTH.name(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIbanFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanFormatException(InvalidIbanFormatException ex) {
        logger.warn("Invalid IBAN format exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(IbanValidationErrorEnum.INVALID_FORMAT.name(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCountryCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCountryCodeException(InvalidCountryCodeException ex) {
        logger.warn("Invalid country code exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(IbanValidationErrorEnum.INVALID_COUNTRY_CODE.name(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIbanChecksumException.class)
    public ResponseEntity<ErrorResponse> handleInvalidIbanChecksumException(InvalidIbanChecksumException ex) {
        logger.warn("Invalid IBAN checksum exception: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(IbanValidationErrorEnum.INVALID_CHECKSUM.name(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}