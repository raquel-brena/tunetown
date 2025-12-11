package com.imd.backend.infra.persistence.jpa.repository.bookyard;

import com.imd.backend.domain.entities.bookyard.BookComment;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCommentRepository extends BaseCommentRepository<BookComment> {
}
