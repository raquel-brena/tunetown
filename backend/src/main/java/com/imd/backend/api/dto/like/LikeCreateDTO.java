package com.imd.backend.api.dto.like;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class LikeCreateDTO {

    @NotNull(message = "O id do Tuneet é obrigatório")
    private UUID tuneetId;

    @NotNull(message = "O id do Profile é obrigatório")
    private UUID profileId;
}
