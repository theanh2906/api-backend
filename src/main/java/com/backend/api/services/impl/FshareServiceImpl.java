package com.backend.api.services.impl;

import com.backend.api.dtos.fshare.*;
import com.backend.api.services.FshareService;
import com.backend.api.shared.FshareConstant;
import com.backend.api.shared.HeaderAttribute;
import com.backend.api.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class FshareServiceImpl implements FshareService {
    private final WebClient webClient = WebClient.create();
    protected String sessionId;
    protected String token;

    @Override
    public void setFshareData(HttpServletRequest request) {
        this.sessionId = request.getHeader("sessionId");
        this.token = request.getHeader("token");
    }

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
        return createUploadRequest(file, filePath, this.token)
                .flatMap(uploadRequest ->
                        webClient
                                .post()
                                .uri(FshareConstant.UPLOAD_URL)
                                .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                                .cookie(HeaderAttribute.SESSION_ID, this.sessionId)
                                .body(Mono.just(uploadRequest), UploadRequest.class).retrieve()
                                .bodyToMono(DownloadLinkResponse.class))
                .flatMap(downloadLink -> {
                    try {
                        return webClient
                                .post()
                                .uri(downloadLink.getLocation())
                                .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                                .accept(MediaType.ALL)
                                .body(Mono.just(file.getBytes()), byte[].class)
                                .retrieve()
                                .bodyToMono(String.class)
                                .mapNotNull(res -> Utils.map(UploadResponse.class, res));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Mono.error(e);
                    }
                });
    }

    @Override
    public Mono<LogoutResponse> logout(String sessionId, String token) {
        return webClient
                .get()
                .uri(FshareConstant.LOGOUT_URL)
                .cookie(HeaderAttribute.SESSION_ID, sessionId)
                .retrieve()
                .bodyToMono(LogoutResponse.class);
    }

    @Override
    public Flux<FileFolderInfo> getFolders() {
        Map<String, Object> params = new HashMap<>();
        params.put("pageIndex", 0);
        params.put("path", "");
        params.put("dirOnly", 0);
        return webClient
                .get()
                .uri(Objects.requireNonNull(Utils.buildUriWithParams(FshareConstant.FILE_FOLDER_INFO_URL, params)))
                .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                .cookie(HeaderAttribute.SESSION_ID, this.sessionId)
                .retrieve()
                .bodyToFlux(FileFolderInfo.class)
                .flatMap(fileFolderInfo -> {
                    if (fileFolderInfo.getType().equals(FshareConstant.FolderType.FOLDER.getValue())) {
                        return getSubFolder(fileFolderInfo).collectList().mapNotNull(subFolder -> {
                            if (fileFolderInfo.getType().equals(FshareConstant.FolderType.FOLDER.getValue())) {
                                fileFolderInfo.setChildren(subFolder);
                            }
                            return fileFolderInfo;
                        });
                    } else {
                        return Mono.just(fileFolderInfo);
                    }
                });
    }

    private Flux<FileFolderInfo> getSubFolder(FileFolderInfo fileFolder) {
        if (fileFolder.getType().equals(FshareConstant.FolderType.FOLDER.getValue())) {
            Map<String, Object> params = new HashMap<>();
            params.put("pageIndex", 0);
            params.put("path", removeFirstSlash(fileFolder.getPath() + "/" + fileFolder.getName()));
            params.put("dirOnly", 0);
            return webClient
                    .get()
                    .uri(Objects.requireNonNull(Utils.buildUriWithParams(FshareConstant.FILE_FOLDER_INFO_URL, params)))
                    .header(HeaderAttribute.USER_AGENT, FshareConstant.USER_AGENT)
                    .cookie(HeaderAttribute.SESSION_ID, this.sessionId)
                    .retrieve()
                    .bodyToFlux(FileFolderInfo.class)
                    .flatMap(fileFolderInfo -> getSubFolder(fileFolderInfo).collectList().mapNotNull(subFolder -> {
                        if (fileFolderInfo.getType().equals(FshareConstant.FolderType.FOLDER.getValue())) {
                            fileFolderInfo.setChildren(subFolder);
                        }
                        return fileFolderInfo;
                    }));
        }
        return Flux.just(fileFolder);
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

    private String removeFirstSlash(String path) {
        if (path.startsWith("//")) {
            return path.substring(2);
        } else if (path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }
}
