package com.backend.api.services;

import com.backend.api.dtos.fshare.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface FshareService {
    void setFshareData(HttpServletRequest request);
    Mono<LoginResponse> login();
    Mono<UserInfoResponse> getUserInfo();
    Mono<UploadResponse> upload(MultipartFile file, String filePath);
    Mono<LogoutResponse> logout(String sessionId, String token);
    Flux<FileFolderInfo> getFolders();
}
