package com.imd.backend.api.controller.filmLog;

import com.imd.backend.api.controller.core.BaseLikeController;
import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.app.service.LikeService;
import com.imd.backend.app.service.filmLog.LikeMovieService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.filmLog.MovieLike;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/filmlog/likes")
public class MovieLikeController extends BaseLikeController<MovieLike, MovieReview> {

    public MovieLikeController(LikeMovieService likeService) {
        super(likeService);
    }

    @Override
    protected MovieLike buildLike(LikeCreateDTO dto) {
        return MovieLike.builder()
                .id(dto.getId())
                .profile(Profile.builder().id(dto.getProfileId()).build())
                .post(MovieReview.builder().id(dto.getTuneetId()).build())
                .build();
    }
}
