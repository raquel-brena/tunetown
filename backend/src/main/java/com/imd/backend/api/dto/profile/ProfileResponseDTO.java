package com.imd.backend.api.dto.profile;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
public record ProfileResponseDTO(String id, String bio, Long idPhoto, String urlPhoto, String favoriteSong,
                                 LocalDateTime createAt) {
}
