package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.infra.persistence.jpa.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getProfile() != null ? user.getProfile().getId() : null
        );
    }
}
