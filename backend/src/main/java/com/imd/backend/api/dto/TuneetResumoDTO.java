package com.imd.backend.api.dto;

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
        String authorId,
        String authorName,
        long totalComments,
        long totalLikes
) {}
