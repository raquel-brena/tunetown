package com.imd.backend.app.dto.core;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBaseLikeDTO {
    private Long id;

    @NotNull(message = "O id do Tuneet é obrigatório")
    private String postId;

    @NotNull(message = "O id do Profile é obrigatório")
    private String profileId;
}
