package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotBlank;

public record ProfileCreateDTO(
        @NotBlank
        Long userId,
        String bio,
        String favoriteSong
) {
}