package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotBlank;

public record ProfileCreateDTO(
        String bio,
        String favoriteSong
) {
}