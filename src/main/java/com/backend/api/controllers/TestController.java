package com.backend.api.controllers;

import com.azure.security.keyvault.secrets.SecretClient;
import com.backend.api.dtos.ResponseDto;
import com.backend.api.dtos.TestDto;
import com.backend.api.services.KeyVaultService;
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
    @GetMapping("/secrets")
    public TestDto getSecret(@RequestParam String key) {
        final TestDto test = new TestDto();
        test.setMessage(keyVaultService.getSecret(key));
        return test;
    }
}
