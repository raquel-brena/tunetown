package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends BaseLikeRepository<Like> {
}
