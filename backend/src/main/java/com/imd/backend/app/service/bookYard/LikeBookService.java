package com.imd.backend.app.service.bookYard;

import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.bookYard.BookLike;
import com.imd.backend.domain.entities.bookYard.BookReview;
import com.imd.backend.domain.entities.filmLog.MovieLike;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.infra.persistence.jpa.repository.bookYard.BookLikeRepository;
import com.imd.backend.infra.persistence.jpa.repository.filmLog.MovieLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeBookService extends BaseLikeService<BookLike, BookReview> {

    public LikeBookService(BookLikeRepository repository) {
        super(repository);
    }
}
