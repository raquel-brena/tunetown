package com.imd.backend.api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentUpdateDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String contentText;
}
