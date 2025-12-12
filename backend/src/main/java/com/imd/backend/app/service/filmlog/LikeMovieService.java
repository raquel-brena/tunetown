package com.imd.backend.app.service.filmlog;

import com.imd.backend.app.dto.movie.CreateMovieLikeDTO;
import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.filmlog.MovieLike;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.infra.persistence.jpa.repository.filmlog.MovieLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeMovieService extends BaseLikeService<MovieLike, MovieReview, CreateMovieLikeDTO> {

    public LikeMovieService(MovieLikeRepository repository) {
        super(repository);
    }


    @Override
    public MovieLike create(MovieLike like) {

        if (like.getProfile().getId().equals(like.getPost().getAuthor().getId())) {
            throw new IllegalArgumentException("Você não pode curtir sua própria review.");
        }

        return super.create(like);
    }
}
