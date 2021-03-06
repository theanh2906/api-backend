package com.backend.api.controllers;

import com.backend.api.dtos.fshare.*;
import com.backend.api.services.FshareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequestMapping("/api/fshare")
public class FshareController {
    @Autowired
    private FshareService fshareService;

    @PostMapping("/login")
    Mono<LoginResponse> login() {
        return fshareService.login();
    }

    @GetMapping("/logout")
    Mono<LogoutResponse> logout(@RequestParam("sessionId") String sessionId, @RequestParam("token") String token) {
        return fshareService.logout(sessionId, token);
    }

    @GetMapping("/user-info")
    Mono<UserInfoResponse> getUserInfo() {
        return fshareService.getUserInfo();
    }

    @PostMapping("/upload")
    Mono<UploadResponse> upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        return fshareService.upload(file, path).doOnError(Throwable::printStackTrace);
    }

    @GetMapping("/folders")
    Flux<FileFolderInfo> getAllFolders() {
        return fshareService
                .getFolders()
                .sort(Comparator.comparing(FileFolderInfo::getType));
    }
}
