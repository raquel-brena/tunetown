package com.imd.backend.api.dto;


import java.time.LocalDateTime;

public record RestResponseMessage(
        LocalDateTime timestamp,
        int status,
        String message,
        Object data
) {

    public RestResponseMessage(Object data, int status, String message) {
        this(LocalDateTime.now(), status, message, data);
    }

    public RestResponseMessage(Object data, int status) {
        this(LocalDateTime.now(), status, "", data);
    }
}