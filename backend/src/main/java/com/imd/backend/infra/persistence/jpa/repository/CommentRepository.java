package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;
import com.imd.backend.infra.persistence.jpa.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByTuneetId(Long tuneetId, Pageable pageable);
}
