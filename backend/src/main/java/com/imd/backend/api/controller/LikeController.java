package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BaseLikeController;
import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.api.dto.like.LikeResponseDTO;
import com.imd.backend.app.service.LikeService;
import com.imd.backend.domain.entities.core.BaseLike;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.mapper.LikeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/likes")
public class LikeController extends BaseLikeController<Like, Tuneet> {

    public LikeController(LikeService likeService) {
        super(likeService);
    }

    @Override
    protected Like buildLike(LikeCreateDTO dto) {
        return Like.builder()
                .id(dto.getId())
                .profile(Profile.builder().id(dto.getProfileId()).build())
                .post(Tuneet.builder().id(dto.getTuneetId()).build())
                .build();
    }
}
