package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.api.dto.like.LikeResponseDTO;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;

public class LikeMapper {

    public static LikeResponseDTO toDTO(Like like) {
        if (like == null) {
            return null;
        }
        return LikeResponseDTO.builder()
                .id(like.getId())
                .tuneetId(like.getTuneet().getId())
                .profileId(like.getProfile().getId())
                .build();
    }



    public static Tuneet toTuneetEntity(String tuneetId) {
        Tuneet t = new Tuneet();
        t.setId(tuneetId);
        return t;
    }

    public static Profile toProfileEntity(String profileId) {
        Profile p = new Profile();
        p.setId(profileId);
        return p;
    }
}
