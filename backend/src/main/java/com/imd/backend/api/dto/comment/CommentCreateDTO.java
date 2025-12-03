package com.imd.backend.api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateDTO {
    @NotNull
    private String tuneetId;

    @NotNull
    private String authorId;

    @NotBlank
    private String contentText;
}
