package com.imd.backend.domain.entities;

import java.util.Date;

public class Comment extends BaseComment {

    public Comment() {
        super();
    }

    public Comment(
            Long id,
            String tuneetId,
            String authorId,
            String authorUsername,
            String contentText,
            Date createdAt
    ) {
        super(id, tuneetId, authorId, authorUsername, contentText, createdAt);
    }
}
