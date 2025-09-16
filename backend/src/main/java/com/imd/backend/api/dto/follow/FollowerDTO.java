package com.imd.backend.api.dto.follow;

import lombok.Builder;

@Builder
public record FollowerDTO(
        String id,
        String username,
        String avatarUrl
) {
}
