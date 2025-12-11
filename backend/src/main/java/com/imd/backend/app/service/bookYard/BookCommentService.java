package com.imd.backend.app.service.bookYard;

import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.TutoResponder;
import com.imd.backend.app.service.core.BaseCommentService;
import com.imd.backend.domain.entities.bookYard.BookComment;
import com.imd.backend.domain.entities.bookYard.BookLike;
import com.imd.backend.domain.entities.bookYard.BookReview;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.filmLog.MovieComment;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.bookYard.BookCommentRepository;
import com.imd.backend.infra.persistence.jpa.repository.bookYard.BookReviewRepository;
import com.imd.backend.infra.persistence.jpa.repository.filmLog.MovieCommentRepository;
import com.imd.backend.infra.persistence.jpa.repository.filmLog.MovieReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class BookCommentService extends BaseCommentService<BookComment, BookReview, BookItem>{

    public BookCommentService(BookCommentRepository repository,
                              TutoResponder tutoResponder,
                              ProfileRepository profileRepository,
                              BookReviewRepository movieRepository) {
        super(repository,tutoResponder, profileRepository, movieRepository);
    }

    @Override
    protected BookComment buildComment(CommentDTO dto, Profile author, BookReview post) {
        return null;
    }
}
