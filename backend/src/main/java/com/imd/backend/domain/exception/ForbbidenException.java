package com.imd.backend.domain.exception;

public class ForbbidenException extends RuntimeException {
    public ForbbidenException(String message) {
        super(message);
    }
}
