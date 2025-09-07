package com.imd.backend.api.dto;

import com.imd.backend.domain.entities.Profile;
import lombok.Builder;

import java.util.Date;

@Builder
public record ProfileResponseDTO(String id, String bio, String avatarUrl, String favoriteSong, Date createAt) {
}
