package com.imd.backend.app.service.filmLog;

import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.filmLog.MovieLike;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.infra.persistence.jpa.repository.filmLog.MovieLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeMovieService extends BaseLikeService<MovieLike, MovieReview> {

    private final MovieLikeRepository repository;

    public LikeMovieService(MovieLikeRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
