package com.imd.backend.app.service.bookyard;

import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.bookyard.BookLike;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.infra.persistence.jpa.repository.bookyard.BookLikeRepository;

import org.springframework.stereotype.Service;

@Service
public class LikeBookService extends BaseLikeService<BookLike, BookReview> {

    public LikeBookService(BookLikeRepository repository) {
        super(repository);
    }
}
