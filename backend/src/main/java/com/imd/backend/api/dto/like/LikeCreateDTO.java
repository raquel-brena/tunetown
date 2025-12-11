package com.imd.backend.api.dto.like;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeCreateDTO {

    private Long id;

    @NotNull(message = "O id do Tuneet é obrigatório")
    private String tuneetId;

    @NotNull(message = "O id do Profile é obrigatório")
    private String profileId;
}
