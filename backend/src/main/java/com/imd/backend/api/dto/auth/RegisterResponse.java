package com.imd.backend.api.dto.auth;

import com.imd.backend.infra.persistence.jpa.entity.UserEntity;

public record RegisterResponse(
   Long id, String username, String email
) {
    public static RegisterResponse fromUser(UserEntity user) {
        return new RegisterResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
