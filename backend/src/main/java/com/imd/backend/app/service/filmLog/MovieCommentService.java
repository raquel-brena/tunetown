package com.imd.backend.app.service.filmLog;

import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.TutoResponder;
import com.imd.backend.app.service.core.BaseCommentService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.filmLog.MovieComment;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.filmLog.MovieCommentRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieCommentService extends BaseCommentService<MovieComment, MovieReview, MovieItem>{

    public MovieCommentService(MovieCommentRepository repository,
                               TutoResponder tutoResponder,
                               ProfileRepository profileRepository,
                               MovieReviewRepository movieRepository) {
        super(repository,tutoResponder, profileRepository, movieRepository);
    }

    @Override
    protected MovieComment buildComment(CommentDTO dto, Profile author, MovieReview post) {
        return null;
    }
}
