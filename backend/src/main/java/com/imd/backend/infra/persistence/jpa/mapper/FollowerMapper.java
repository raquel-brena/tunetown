package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.follow.FollowerDTO;
import com.imd.backend.domain.entities.core.Profile;

public class FollowerMapper {

    public static FollowerDTO toDTO(Profile profile) {
        if (profile == null) return null;
        return FollowerDTO.builder()
                .id(profile.getId())
                .username(profile.getUser() != null ? profile.getUser().getUsername() : null)
                .avatarUrl(profile.getPhoto() != null ? profile.getPhoto().getUrl() : null)
                .build();
    }
}
