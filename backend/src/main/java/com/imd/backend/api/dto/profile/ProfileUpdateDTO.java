package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotBlank;

public record ProfileUpdateDTO(
        @NotBlank String id,
        String bio,
        String favoriteSong
) {
}
