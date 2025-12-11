package com.imd.backend.api.dto.auth;


import com.imd.backend.domain.entities.core.User;

public record RegisterResponse(
   String id, String username, String email
) {
    public static RegisterResponse fromUser(User user) {
        return new RegisterResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
