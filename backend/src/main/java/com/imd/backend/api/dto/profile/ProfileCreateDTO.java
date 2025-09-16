package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotNull;

public record ProfileCreateDTO(
        @NotNull
        Long userId,
        String bio,
        String favoriteSong
) {
}