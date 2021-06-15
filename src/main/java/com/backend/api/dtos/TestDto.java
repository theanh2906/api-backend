package com.backend.api.dtos;

public class TestDto {
    private String message;

    public TestDto() {
    }

    public TestDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
