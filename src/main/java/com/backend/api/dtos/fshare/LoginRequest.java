package com.backend.api.dtos.fshare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @JsonProperty("user_email")
    private String email;
    private String password;
    @JsonProperty("app_key")
    private String appKey;
}
