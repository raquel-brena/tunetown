package com.imd.backend.infra.persistence.jpa.repository.filmlog;

import com.imd.backend.domain.entities.filmlog.MovieComment;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCommentRepository extends BaseCommentRepository<MovieComment> {
}
