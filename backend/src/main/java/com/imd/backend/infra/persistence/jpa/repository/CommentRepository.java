package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseCommentRepository<Comment> {
}
