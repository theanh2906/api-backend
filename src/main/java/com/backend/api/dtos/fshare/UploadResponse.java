package com.backend.api.dtos.fshare;

import lombok.Data;

@Data
public class UploadResponse {
    private Integer secure;
    private Integer size;
    private String url;
    private String name;
    private String desc;
}
