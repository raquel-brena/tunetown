package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends BaseLikeRepository<Like> {
}
