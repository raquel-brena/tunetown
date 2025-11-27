package com.imd.backend.infra.persistence.jpa.mapper;

import java.util.UUID;

import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.domain.entities.core.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            user.getProfile().getId()
        );
    }
}
