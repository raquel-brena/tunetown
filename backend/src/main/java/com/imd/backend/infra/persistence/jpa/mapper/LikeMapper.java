package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.api.dto.like.LikeResponseDTO;
import com.imd.backend.domain.entities.Like;
import com.imd.backend.infra.persistence.jpa.entity.LikeEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

public class LikeMapper {

    public static LikeResponseDTO toDTO(Like like) {
        return LikeResponseDTO.builder()
                .id(like.getId())
                .tuneetId(like.getTuneetId())
                .profileId(like.getProfileId())
                .build();
    }

    public static Like toDomain(LikeCreateDTO dto) {
        return new Like(null, dto.getTuneetId(), dto.getProfileId());
    }

    public static Like toDomain(LikeEntity entity) {
        return new Like(entity.getId(), entity.getTuneet().getId(), entity.getProfile().getId());
    }

    public static LikeEntity toEntity(Like domain) {
        return LikeEntity.builder()
                .id(domain.getId())
                .tuneet(toTuneetEntity(domain.getTuneetId()))
                .profile(toProfileEntity(domain.getProfileId()))
                .build();
    }

    public static TuneetEntity toTuneetEntity(String tuneetId) {
        TuneetEntity t = new TuneetEntity();
        t.setId(tuneetId);
        return t;
    }

    public static ProfileEntity toProfileEntity(String profileId) {
        ProfileEntity p = new ProfileEntity();
        p.setId(profileId);
        return p;
    }
}
