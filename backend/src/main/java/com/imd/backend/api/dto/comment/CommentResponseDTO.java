package com.imd.backend.api.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDTO {
    private Long id;
    private String tuneetId;
    private String authorUsername;
    private String contentText;
    private LocalDateTime createdAt;
}
