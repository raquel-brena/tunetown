package com.imd.backend.domain.entities;

import lombok.Data;

@Data
public class Like {
    private final Long id;
    private final String tuneetId;
    private final String profileId;
}
