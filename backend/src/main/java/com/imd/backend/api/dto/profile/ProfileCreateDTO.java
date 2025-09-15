package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotBlank;

public record ProfileCreateDTO(
        @NotBlank String username,
        String bio,
        String favoriteSong
) {
}