package com.imd.backend.api.dto.comment;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class CommentResponseDTO {
    private Long id;
    private String tuneetId;
    private String authorId;
    private String contentText;
    private Date createdAt;
}
