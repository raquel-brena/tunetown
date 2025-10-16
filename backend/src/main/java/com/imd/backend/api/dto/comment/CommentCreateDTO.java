package com.imd.backend.api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentCreateDTO {
    @NotNull
    private UUID tuneetId;

    @NotNull
    private UUID authorId;

    @NotBlank
    private String contentText;
}
