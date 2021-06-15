package com.backend.api.dtos;

public class ResponseDto {
    private String message;

    public ResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseDto(String message) {
        this.message = message;
    }
}
