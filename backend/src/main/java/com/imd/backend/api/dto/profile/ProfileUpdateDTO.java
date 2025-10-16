package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ProfileUpdateDTO(
        @NotBlank UUID id,
        String bio,
        String favoriteSong
) {
}
