package com.imd.backend.api.dto.user;

import java.util.UUID;

public record UserDTO (Long id, String username, String email, UUID profileId){
}
