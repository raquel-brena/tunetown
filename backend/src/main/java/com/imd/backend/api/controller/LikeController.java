package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BaseLikeController;
import com.imd.backend.app.dto.core.CreateBaseLikeDTO;
import com.imd.backend.app.service.LikeService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/likes")
public class LikeController extends BaseLikeController<Like, Tuneet, CreateBaseLikeDTO> {

    public LikeController(LikeService likeService) {
        super(likeService);
    }

    @Override
    protected Like buildLike(CreateBaseLikeDTO dto) {
        return Like.builder()
                .id(dto.getId())
                .profile(Profile.builder().id(dto.getProfileId()).build())
                .post(Tuneet.builder().id(dto.getPostId()).build())
                .build();
    }
}
