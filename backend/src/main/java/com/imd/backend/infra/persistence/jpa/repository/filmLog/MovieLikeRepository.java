package com.imd.backend.infra.persistence.jpa.repository.filmLog;

import com.imd.backend.domain.entities.filmLog.MovieLike;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieLikeRepository extends BaseLikeRepository<MovieLike> {
}
