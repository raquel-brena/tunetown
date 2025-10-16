package com.imd.backend.domain.entities;

import lombok.Data;

import java.util.UUID;

@Data
public class Like {
    private final Long id;
    private final UUID tuneetId;
    private final UUID profileId;
}
