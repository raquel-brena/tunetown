package com.imd.backend.api.dto.like;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LikeResponseDTO {

    private Long id;
    private UUID tuneetId;
    private UUID profileId;
}
