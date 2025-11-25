package com.imd.backend.domain.entities;

import lombok.Data;

@Data
public class Like extends BaseLike {
    public Like(){
        super();
    }

    public Like(Long id, String tuneetId, String profileId) {
        super(id, tuneetId, profileId);

    }
}
