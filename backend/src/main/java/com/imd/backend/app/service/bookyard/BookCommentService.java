package com.imd.backend.app.service.bookyard;

import com.imd.backend.app.dto.bookYard.CreateBookCommentDTO;
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
public class BookCommentService extends BaseCommentService<BookComment, BookReview, BookItem, CreateBookCommentDTO>{

    public BookCommentService(BookCommentRepository repository,
                              BookResponderService bookResponderService,
                              ProfileRepository profileRepository,
                              BookRepository movieRepository) {
        super(repository, bookResponderService, profileRepository, movieRepository);
    }

    @Override
    protected BookComment buildComment(CreateBookCommentDTO dto, Profile author, BookReview post) {
        if (dto.getPageNumber() != null && dto.getPageNumber() < 0) {
            throw new IllegalArgumentException("Número da página não pode ser negativo.");
        }

        if (dto.getPageNumber() != null &&
                post.getBookPageCount() != null &&
                dto.getPageNumber() > post.getBookPageCount()) {
            throw new IllegalArgumentException("Página informada excede a quantidade total do livro.");
        }

        BookComment comment = BookComment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())
                .chapterName(dto.getChapterName())
                .pageNumber(dto.getPageNumber())
                .author(author)
                .post(post)
                .build();

        comment.validateAssociation();

        return comment;
    }
}
