package com.imd.backend.api.dto.profile;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProfileResponseDTO(String id, String bio, Long idPhoto, String urlPhoto, String favoriteSong,
                                 Date createAt) {
}
