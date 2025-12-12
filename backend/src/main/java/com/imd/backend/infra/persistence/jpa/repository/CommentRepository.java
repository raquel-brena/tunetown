package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseCommentRepository<Comment> {
}
