package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.profile.ProfileCreateDTO;
import com.imd.backend.api.dto.profile.ProfileResponseDTO;
import com.imd.backend.api.dto.profile.ProfileUpdateDTO;
import com.imd.backend.domain.entities.Profile;
import com.imd.backend.infra.persistence.jpa.entity.FileEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.entity.User;

import java.util.Date;


public class ProfileMapper {

    public static Profile toDomain(ProfileEntity entity) {
        return Profile.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .bio(entity.getBio())
                .photo(FileMapper.toDomain(entity.getPhoto()))
                .favoriteSong(entity.getFavoriteSong())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static ProfileEntity toEntity(ProfileCreateDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setBio(dto.bio());
        entity.setFavoriteSong(dto.favoriteSong());
        entity.setCreatedAt(new Date());

        if (dto.userId() != null) {
            User user = new User();
            user.setId(dto.userId());
            entity.setUser(user);
        }

        return entity;
    }

    public static Profile toDomain(ProfileUpdateDTO entity) {
        return Profile.builder()
                .id(entity.id())
                .bio(entity.bio())
                .favoriteSong(entity.favoriteSong())
                .build();
    }

    public static ProfileResponseDTO toDTO(Profile domain) {
        return ProfileResponseDTO.builder()
                .id(domain.getId())
                .bio(domain.getBio())
                .idPhoto(domain.getPhoto() != null ? domain.getPhoto().getId() : null)
                .favoriteSong(domain.getFavoriteSong())
                .createAt(domain.getCreatedAt())
                .build();
    }

    public static Profile toDomain(ProfileResponseDTO dto) {
        return Profile.builder()
                .id(dto.id())
                .bio(dto.bio())
                .photo(dto.idPhoto() != null ? FileEntity.builder().id(dto.idPhoto()).build() : null)
                .favoriteSong(dto.favoriteSong())
                .createdAt(dto.createAt())
                .build();
    }
}

