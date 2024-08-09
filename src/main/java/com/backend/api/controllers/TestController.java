package com.backend.api.controllers;

import com.azure.security.keyvault.secrets.SecretClient;
import com.backend.api.dtos.ResponseDto;
import com.backend.api.dtos.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {
    @Autowired
    private SecretClient secretClient;
    @GetMapping("/hello")
    public TestDto sayHello() {
        final TestDto test = new TestDto();
        test.setMessage(secretClient.getSecret("clientId").getValue());
        return test;
    }

    @PostMapping("/fshare/login")
    public Mono<ResponseDto> login(@RequestBody TestDto testDto) {
        final ResponseDto response = new ResponseDto();
        response.setMessage("login success");
        return Mono.just(response);
    }
}
