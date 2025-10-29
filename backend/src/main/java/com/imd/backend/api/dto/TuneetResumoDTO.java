package com.imd.backend.api.dto;

import com.imd.backend.infra.persistence.jpa.entity.UserEntity;

import java.time.LocalDateTime;

public record TuneetResumoDTO(
        String id,
        String contentText,
        String tunableItemArtist,
        String tunableItemTitle,
        String tunableItemArtworkUrl,
        String tunableItemId,
        String tunableItemPlataform,
        String tunableItemType,
        LocalDateTime createdAt,
        String authorName,
        String profileId,
        String email,
        String authorId,
        long totalComments,
        long totalLikes,
        String bio,
        long totalFollowers,
        long totalFollowing,
        String urlPhoto
) {}
