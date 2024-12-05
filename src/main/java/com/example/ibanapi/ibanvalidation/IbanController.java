package com.example.ibanapi.ibanvalidation;

import com.example.ibanapi.ibanvalidation.model.IbanValidationRequest;
import com.example.ibanapi.ibanvalidation.model.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iban")
public class IbanController {

    private static final Logger logger = LoggerFactory.getLogger(IbanController.class);

    private final IbanService ibanService;

    public IbanController(IbanService ibanService) {
        this.ibanService = ibanService;
    }

    @PostMapping("/validate")
    public ResponseEntity<SuccessResponse> validateIban(@RequestBody IbanValidationRequest request) {
        logger.debug("Received IBAN validation request: {}", request);
        SuccessResponse response = ibanService.validateIban(request.getIban());
        logger.debug("Returning IBAN validation response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Status OK");
    }
}