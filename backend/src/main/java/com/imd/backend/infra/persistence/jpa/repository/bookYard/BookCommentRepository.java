package com.imd.backend.infra.persistence.jpa.repository.bookYard;

import com.imd.backend.domain.entities.bookYard.BookComment;
import com.imd.backend.domain.entities.filmLog.MovieComment;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCommentRepository extends BaseCommentRepository<BookComment> {
}
