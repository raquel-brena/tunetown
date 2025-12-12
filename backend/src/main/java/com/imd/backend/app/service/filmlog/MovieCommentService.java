package com.imd.backend.app.service.filmlog;

import com.imd.backend.app.dto.movie.CreateMovieCommentDTO;
import com.imd.backend.app.service.core.BaseCommentService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.filmlog.MovieComment;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.MovieRepository;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.filmlog.MovieCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieCommentService extends BaseCommentService<MovieComment, MovieReview, MovieItem, CreateMovieCommentDTO> {

    public MovieCommentService(MovieCommentRepository repository,
            MovieResponderService movieResponderService,
            ProfileRepository profileRepository,
            MovieRepository movieRepository) {
        super(repository, movieResponderService, profileRepository, movieRepository);
    }

    @Override
    protected MovieComment buildComment(CreateMovieCommentDTO dto, Profile author, MovieReview post) {
        if (dto.getMinuteMark() != null && dto.getMinuteMark() < 0) {
            throw new IllegalArgumentException("O minuto marcado não pode ser negativo.");
        }

        return MovieComment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())

                .author(author)
                .post(post)

                .minuteMark(dto.getMinuteMark())

                .build();
    }
}
