package com.imd.backend.infra.persistence.jpa.repository.bookyard;

import com.imd.backend.domain.entities.bookyard.BookLike;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLikeRepository extends BaseLikeRepository<BookLike> {
}
