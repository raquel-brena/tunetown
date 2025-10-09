package com.imd.backend.domain.entities;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
public class Comment {
    private final Long id;
    private final String tuneetId;
    private final String authorId;
    private String contentText;
    private final Date createdAt;
}