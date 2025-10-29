package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByTuneetId(String tuneetId, Pageable pageable);
}
