package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.ProfileResponseDTO;
import com.imd.backend.domain.entities.Profile;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;

public class ProfileMapper {
    public static Profile toDomain(ProfileEntity entity) {
        return Profile.builder()
                .id(entity.getId())
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .favoriteSong(entity.getFavoriteSong())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static ProfileEntity toEntity(Profile domain) {
        return new ProfileEntity(
                domain.getId(),
                domain.getBio(),
                domain.getAvatarUrl(),
                domain.getFavoriteSong(),
                domain.getCreatedAt()
        );
    }

    public static ProfileResponseDTO toDTO(Profile domain) {
        return ProfileResponseDTO.builder()
                .id(domain.getId())
                .bio(domain.getBio())
                .avatarUrl(domain.getAvatarUrl())
                .favoriteSong(domain.getFavoriteSong())
                .createAt(domain.getCreatedAt())
                .build();
    }

    public static Profile toDomain(ProfileResponseDTO dto) {
        return Profile.builder()
                .id(dto.id())
                .bio(dto.bio())
                .avatarUrl(dto.avatarUrl())
                .favoriteSong(dto.favoriteSong())
                .createdAt(dto.createAt())
                .build();
    }
}

