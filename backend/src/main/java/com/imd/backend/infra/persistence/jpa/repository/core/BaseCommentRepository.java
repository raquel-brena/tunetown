package com.imd.backend.infra.persistence.jpa.repository.core;

import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.BaseLike;
import com.imd.backend.domain.entities.tunetown.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseCommentRepository<T extends BaseComment> extends JpaRepository<T, Long> {
    Page<T> findByPostId(String tuneetId, Pageable pageable);
}
