package com.imd.backend.infra.persistence.jpa.repository.filmLog;

import com.imd.backend.domain.entities.filmLog.MovieComment;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCommentRepository extends BaseCommentRepository<MovieComment> {
}
