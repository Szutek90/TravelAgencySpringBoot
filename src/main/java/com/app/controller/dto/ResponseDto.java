package com.app.controller.dto;

public record ResponseDto<T>(T data, String error) {
    public ResponseDto(T data) {
        this(data, null);
    }

    public ResponseDto(String error) {
        this(null, error);
    }
}
