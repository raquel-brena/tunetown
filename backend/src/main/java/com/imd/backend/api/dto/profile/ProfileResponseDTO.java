package com.imd.backend.api.dto.profile;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ProfileResponseDTO(UUID id, String bio, Long idPhoto, String urlPhoto, String favoriteSong,
                                 Date createAt) {
}
