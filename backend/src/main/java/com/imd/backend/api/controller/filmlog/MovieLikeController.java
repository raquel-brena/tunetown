package com.imd.backend.api.controller.filmlog;

import com.imd.backend.api.controller.core.BaseLikeController;
import com.imd.backend.app.dto.movie.CreateMovieLikeDTO;
import com.imd.backend.app.service.filmlog.LikeMovieService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.filmlog.MovieLike;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/filmlog/likes")
public class MovieLikeController extends BaseLikeController<
    MovieLike,
    MovieReview,
        CreateMovieLikeDTO
> {

    public MovieLikeController(LikeMovieService likeService) {
        super(likeService);
    }

    @Override
    protected MovieLike buildLike(CreateMovieLikeDTO dto) {
        return MovieLike.builder()
                .id(dto.getId())
                .reaction(dto.getReaction())
                .profile(Profile.builder().id(dto.getProfileId()).build())
                .post(MovieReview.builder().id(dto.getPostId()).build())
                .build();
    }
}
