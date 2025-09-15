package com.imd.backend.api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "não deve estar em branco")
        String login,
        @NotBlank(message = "não deve estar em branco")
        String password
) {}
