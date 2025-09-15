package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.follow.FollowerDTO;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;

public class FollowerMapper {

    public static FollowerDTO toDTO(ProfileEntity profile) {
        if (profile == null) return null;
        return FollowerDTO.builder()
                .id(profile.getId())
                .username(profile.getUser() != null ? profile.getUser().getUsername() : null)
                .avatarUrl(profile.getPhoto() != null ? profile.getPhoto().getUrl() : null)
                .build();
    }
}
