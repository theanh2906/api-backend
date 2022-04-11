package com.backend.api.dtos.fshare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LogoutResponse {
    private Integer code;
    @JsonProperty("msg")
    private String message;
}
