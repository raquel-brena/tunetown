package com.imd.backend.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
    @NotBlank(message = "não deve estar em branco")
    @Size(min = 3, message = "deve ter no mínimo 3 caracteres")
    String username,
    @NotBlank(message = "não deve estar em branco")
    @Email(message = "deve ser um endereço de email válido")
    String email,
    @NotBlank(message = "não deve estar em branco")
    @Size(min = 6, max = 64, message = "deve ter entre 6 e 64 caracteres")
    String password
) {}
