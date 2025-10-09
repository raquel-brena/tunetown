package com.imd.backend.api.dto.like;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResponseDTO {

    private Long id;
    private String tuneetId;
    private String profileId;
}
