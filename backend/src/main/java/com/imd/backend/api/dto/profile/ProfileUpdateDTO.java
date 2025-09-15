package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotBlank;

public record ProfileUpdateDTO(
        @NotBlank String id,
        String username,
        String bio,
        String favoriteSong
) {
}
