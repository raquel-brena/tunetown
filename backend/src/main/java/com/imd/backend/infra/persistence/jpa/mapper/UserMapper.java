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
            entity.getProfile() != null ? entity.getProfile().getId() : null
        );
    }

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
            user.getId().toString(),
            user.getEmail(),
            user.getUsername(),
            user.getProfileId()
        );
    }
}
