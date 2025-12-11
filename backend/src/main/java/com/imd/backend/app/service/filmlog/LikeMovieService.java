package com.imd.backend.app.service.filmlog;

import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.filmlog.MovieLike;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.infra.persistence.jpa.repository.filmlog.MovieLikeRepository;

import org.springframework.stereotype.Service;

@Service
public class LikeMovieService extends BaseLikeService<MovieLike, MovieReview> {

    public LikeMovieService(MovieLikeRepository repository) {
        super(repository);
    }
}
