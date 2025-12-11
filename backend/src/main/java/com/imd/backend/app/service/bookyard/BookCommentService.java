package com.imd.backend.app.service.bookyard;

import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.core.BaseCommentService;
import com.imd.backend.domain.entities.bookyard.BookComment;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;
import com.imd.backend.infra.persistence.jpa.repository.BookRepository;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.bookyard.BookCommentRepository;

import org.springframework.stereotype.Service;

@Service
public class BookCommentService extends BaseCommentService<BookComment, BookReview, BookItem>{

    public BookCommentService(BookCommentRepository repository,
                              BookResponderService bookResponderService,
                              ProfileRepository profileRepository,
                              BookRepository movieRepository) {
        super(repository, bookResponderService, profileRepository, movieRepository);
    }

    @Override
    protected BookComment buildComment(CommentDTO dto, Profile author, BookReview post) {
        return null;
    }
}
