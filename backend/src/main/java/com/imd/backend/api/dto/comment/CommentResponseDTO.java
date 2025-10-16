package com.imd.backend.api.dto.comment;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class CommentResponseDTO {
    private Long id;
    private UUID tuneetId;
    private UUID authorId;
    private String contentText;
    private Date createdAt;
}
