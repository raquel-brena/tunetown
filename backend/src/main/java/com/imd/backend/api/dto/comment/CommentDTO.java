package com.imd.backend.api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    @NotNull
    private String tuneetId;

    @NotNull
    private String authorId;

    @NotBlank
    private String contentText;
}
