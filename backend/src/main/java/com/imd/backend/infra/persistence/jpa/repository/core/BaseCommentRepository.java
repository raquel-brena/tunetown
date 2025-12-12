package com.imd.backend.infra.persistence.jpa.repository.core;

import com.imd.backend.domain.entities.core.BaseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseCommentRepository<T extends BaseComment> extends JpaRepository<T, Long> {
    Page<T> findByPostId(String tuneetId, Pageable pageable);
}
