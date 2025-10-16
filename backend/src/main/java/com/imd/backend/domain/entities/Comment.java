package com.imd.backend.domain.entities;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Comment {
    private final Long id;
    private final UUID tuneetId;
    private final UUID authorId;
    private String contentText;
    private final Date createdAt;
}