package com.imd.backend.api.dto.profile;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProfileResponseDTO(String id, String username, String bio, String avatarUrl, String favoriteSong,
                                 Date createAt) {
}
