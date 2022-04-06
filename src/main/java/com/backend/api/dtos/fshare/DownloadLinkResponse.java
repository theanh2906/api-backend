package com.backend.api.dtos.fshare;

import lombok.Data;

@Data
public class DownloadLinkResponse {
    private Integer code;
    private String location;
}
