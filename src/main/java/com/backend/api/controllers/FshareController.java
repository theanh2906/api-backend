package com.backend.api.controllers;

import com.backend.api.dtos.fshare.LoginResponse;
import com.backend.api.dtos.fshare.UploadRequest;
import com.backend.api.dtos.fshare.UploadResponse;
import com.backend.api.dtos.fshare.UserInfoResponse;
import com.backend.api.services.FshareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/fshare")
public class FshareController {
    @Autowired
    private FshareService fshareService;

    @PostMapping("/login")
    Mono<LoginResponse> login() {
        return fshareService.login();
    }

    @GetMapping("/user-info")
    Mono<UserInfoResponse> getUserInfo() {
        return fshareService.getUserInfo();
    }

    @PostMapping("/upload")
    Mono<UploadResponse> upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        return fshareService.upload(file, path).doOnError(Throwable::printStackTrace);
    }
}
