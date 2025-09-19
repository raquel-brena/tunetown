package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;

public class UserMapper {

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
