package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.profile.ProfileCreateDTO;
import com.imd.backend.api.dto.profile.ProfileResponseDTO;
import com.imd.backend.api.dto.profile.ProfileUpdateDTO;
import com.imd.backend.domain.entities.core.MediaFile;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.core.User;

import java.time.LocalDateTime;


public class ProfileMapper {

    public static Profile toDomain(Profile entity) {
        User user = entity.getUser();

        return Profile.builder()
                .id(entity.getId())
                .user(user)
                .bio(entity.getBio())
                .photo(entity.getPhoto())
                .favoriteSong(entity.getFavoriteSong())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static Profile toEntity(ProfileCreateDTO dto) {
        Profile entity = new Profile();
        entity.setBio(dto.bio());
        entity.setFavoriteSong(dto.favoriteSong());
        entity.setCreatedAt(LocalDateTime.now());

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
                .urlPhoto(domain.getPhoto() != null ? domain.getPhoto().getUrl() : null)
                .favoriteSong(domain.getFavoriteSong())
                .createAt(domain.getCreatedAt())
                .build();
    }

    public static Profile toDomain(ProfileResponseDTO dto) {
        return Profile.builder()
                .id(dto.id())
                .bio(dto.bio())
                .photo(dto.idPhoto() != null ? MediaFile.builder().id(dto.idPhoto()).build() : null)
                .favoriteSong(dto.favoriteSong())
                .createdAt(dto.createAt())
                .build();
    }
}

