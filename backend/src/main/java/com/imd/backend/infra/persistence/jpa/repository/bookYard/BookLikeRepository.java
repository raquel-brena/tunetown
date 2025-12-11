package com.imd.backend.infra.persistence.jpa.repository.bookYard;

import com.imd.backend.domain.entities.bookYard.BookLike;
import com.imd.backend.domain.entities.filmLog.MovieLike;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLikeRepository extends BaseLikeRepository<BookLike> {
}
