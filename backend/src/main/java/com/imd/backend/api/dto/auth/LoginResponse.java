package com.imd.backend.api.dto.auth;

import com.imd.backend.api.dto.user.UserDTO;

public record LoginResponse(
        String accessToken,
        UserDTO userDTO
){}
