package com.imd.backend.infra.persistence.jpa.mapper;

import java.util.UUID;

import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.domain.entities.User;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;

public class UserMapper {
    public static UserEntity domainToEntity(
        User user
    ) {
        if(user == null) return null;

        return new UserEntity(
            user.getId().toString(),
            user.getEmail(),
            user.getUsername(),
            user.getPassword()
        );
    }

    public static User entityToDomain(UserEntity entity) {
        if(entity == null) return null;

        return User.rebuild(
            UUID.fromString(entity.getId()),
            entity.getEmail(),
            entity.getUsername(),
            entity.getPassword(),
            entity.getProfile().getId()
        );
    }

    public static UserDTO toDTO(UserEntity user) {
        if (user == null) return null;
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            user.getProfile() != null ? user.getProfile().getId() : null
        );
    }
}
