package com.backend.api.controllers;

import com.azure.security.keyvault.secrets.SecretClient;
import com.backend.api.dtos.ResponseDto;
import com.backend.api.dtos.TestDto;
import com.backend.api.models.MessageObject;
import com.backend.api.services.KeyVaultService;
import com.backend.api.services.ServiceBusMessagingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {
    @Autowired
    private KeyVaultService keyVaultService;
    @Autowired
    private ServiceBusMessagingService messagingService;
    @GetMapping("/secrets")
    public TestDto getSecret(@RequestParam String key) {
        final TestDto test = new TestDto();
        test.setMessage(keyVaultService.getSecret(key));
        return test;
    }
    @PostMapping("/queue")
    public ResponseEntity<?> sendMessage(@RequestBody MessageObject message) {
        try {
            messagingService.sendMessage(message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to send message!");
        }
    }
}
