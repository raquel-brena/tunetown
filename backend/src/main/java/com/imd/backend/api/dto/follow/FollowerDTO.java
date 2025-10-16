package com.imd.backend.api.dto.follow;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FollowerDTO(
        UUID id,
        String username,
        String avatarUrl
) {
}
