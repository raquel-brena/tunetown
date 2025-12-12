package com.imd.backend.app.service.bookyard;

import com.imd.backend.app.dto.bookYard.CreateBookLikeDTO;
import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.bookyard.BookLike;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.infra.persistence.jpa.repository.bookyard.BookLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeBookService extends BaseLikeService<BookLike, BookReview, CreateBookLikeDTO> {

    public LikeBookService(BookLikeRepository repository) {
        super(repository);
    }

    @Override
    public BookLike create(BookLike like) {

        BookReview review = (BookReview) like.getPost();

        if ("WANT_TO_READ".equalsIgnoreCase(review.getReadingStatus())) {
            throw new IllegalStateException("Você não pode curtir um livro que ainda não começou a ler.");
        }

        if (review.getBookPageCount() != null && review.getBookPageCount() < 30) {
            throw new IllegalStateException("Livretos com menos de 30 páginas não podem ser curtidos.");
        }

        return super.create(like);
    }
}
