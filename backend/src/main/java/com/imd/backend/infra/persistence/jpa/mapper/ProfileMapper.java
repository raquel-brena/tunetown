package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.profile.ProfileCreateDTO;
import com.imd.backend.api.dto.profile.ProfileResponseDTO;
import com.imd.backend.api.dto.profile.ProfileUpdateDTO;
import com.imd.backend.app.service.S3Service;
import com.imd.backend.domain.entities.Profile;
import com.imd.backend.infra.persistence.jpa.entity.FileEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;

import java.time.Duration;

public class ProfileMapper {

    public static Profile toDomain(ProfileEntity entity) {
        return Profile.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .bio(entity.getBio())
                .photo(FileMapper.toDomain(entity.getPhoto()))
                .favoriteSong(entity.getFavoriteSong())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static Profile toDomain(ProfileCreateDTO entity) {
        return Profile.builder()
                .bio(entity.bio())
                .username(entity.username())
                .favoriteSong(entity.favoriteSong())
                .build();
    }

    public static Profile toDomain(ProfileUpdateDTO entity) {
        return Profile.builder()
                .id(entity.id())
                .bio(entity.bio())
                .username(entity.username())
                .favoriteSong(entity.favoriteSong())
                .build();
    }


    public static ProfileEntity toEntity(Profile domain) {
        return new ProfileEntity(
                domain.getId(),
                domain.getUsername(),
                domain.getBio(),
                domain.getPhoto(),
                domain.getFavoriteSong(),
                domain.getCreatedAt()
        );
    }

    public static ProfileResponseDTO toDTO(Profile domain) {
        return ProfileResponseDTO.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .bio(domain.getBio())
                .avatarUrl(domain.getPhoto() != null ? domain.getPhoto().getUrl() : null)
                .favoriteSong(domain.getFavoriteSong())
                .createAt(domain.getCreatedAt())
                .build();
    }

    public static Profile toDomain(ProfileResponseDTO dto) {
        return Profile.builder()
                .id(dto.id())
                .bio(dto.bio())
                .username(dto.username())
                .photo(dto.avatarUrl() != null ? FileEntity.builder().url(dto.avatarUrl()).build() : null)
                .favoriteSong(dto.favoriteSong())
                .createdAt(dto.createAt())
                .build();
    }
}

