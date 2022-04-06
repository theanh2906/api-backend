package com.backend.api.services.impl;

import com.backend.api.dtos.fshare.*;
import com.backend.api.services.FshareService;
import com.backend.api.shared.FshareConstant;
import com.backend.api.shared.HeaderAttribute;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class FshareServiceImpl implements FshareService {
    private WebClient webClient = WebClient.create();

    @Override
    public Mono<LoginResponse> login() {
        return webClient
                .post()
                .uri(FshareConstant.LOGIN_URL)
                .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                .body(Mono.just(FshareConstant.LOGIN_REQUEST), LoginRequest.class)
                .retrieve().bodyToMono(LoginResponse.class);
    }

    @Override
    public Mono<UserInfoResponse> getUserInfo() {
        return login().flatMap(loginResponse -> webClient
                .get()
                .uri(FshareConstant.USER_INFO_URL)
                .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                .cookie(HeaderAttribute.SESSION_ID, loginResponse.getSessionId())
                .retrieve()
                .bodyToMono(UserInfoResponse.class));
    }

    @Override
    public Mono<UploadResponse> upload(MultipartFile file, String filePath) {
        return login().flatMap(loginResponse ->
                createUploadRequest(file, filePath, loginResponse.getToken())
                        .flatMap(uploadRequest ->
                                webClient
                                        .post()
                                        .uri(FshareConstant.UPLOAD_URL)
                                        .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                                        .cookie(HeaderAttribute.SESSION_ID, loginResponse.getSessionId())
                                        .body(Mono.just(uploadRequest), UploadRequest.class).retrieve()
                                        .bodyToMono(DownloadLinkResponse.class))
                        .flatMap(downloadLink -> {
                            try {
                                return webClient
                                        .post()
                                        .uri(downloadLink.getLocation())
                                        .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                                        .body(Mono.just(file.getBytes()), byte[].class)
                                        .retrieve()
                                        .bodyToMono(UploadResponse.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return Mono.error(e);
                            }
                        }));
    }

    private Mono<UploadRequest> createUploadRequest(MultipartFile file, String filePath, String token) {
        final UploadRequest uploadRequest = new UploadRequest();
        uploadRequest.setName(file.getOriginalFilename());
        uploadRequest.setSecured(1);
        uploadRequest.setPath(filePath);
        uploadRequest.setSize(String.valueOf(file.getSize()));
        uploadRequest.setToken(token);
        return Mono.just(uploadRequest);
    }
}
