package com.imd.backend.api.dto.profile;

import jakarta.validation.constraints.NotNull;

public record ProfileCreateDTO(
        @NotNull
        String userId,
        String bio,
        String favoriteSong
) {
}