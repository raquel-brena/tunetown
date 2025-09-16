package com.imd.backend.api.dto.profile;

public record ProfileCreateDTO(
        String bio,
        String favoriteSong
) {
}