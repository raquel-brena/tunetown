package com.imd.backend.infra.persistence.jpa.repository.core;

import com.imd.backend.domain.entities.core.BaseLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseLikeRepository<T extends BaseLike> extends JpaRepository<T, Long> {
    Page<T> findByPostId(String postId, Pageable pageable);

    Optional<T> findByProfileIdAndPostId(String profileId, String postId);
}
