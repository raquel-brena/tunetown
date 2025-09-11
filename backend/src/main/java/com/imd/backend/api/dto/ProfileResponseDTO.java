package com.imd.backend.api.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProfileResponseDTO(String id, String bio, String avatarUrl, String favoriteSong, Date createAt) {
}
