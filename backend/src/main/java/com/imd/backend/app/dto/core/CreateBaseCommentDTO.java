package com.imd.backend.app.dto.core;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBaseCommentDTO {
    private Long id;

    @NotNull
    private String postId;

    @NotNull
    private String authorId;

    @NotBlank
    private String contentText;
}
