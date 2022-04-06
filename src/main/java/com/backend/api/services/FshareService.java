package com.backend.api.services;

import com.backend.api.dtos.fshare.LoginResponse;
import com.backend.api.dtos.fshare.UploadResponse;
import com.backend.api.dtos.fshare.UserInfoResponse;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface FshareService {
    Mono<LoginResponse> login();
    Mono<UserInfoResponse> getUserInfo();
    Mono<UploadResponse> upload(MultipartFile file, String filePath);
}
