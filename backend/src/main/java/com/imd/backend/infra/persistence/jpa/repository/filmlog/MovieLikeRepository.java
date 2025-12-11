package com.imd.backend.infra.persistence.jpa.repository.filmlog;

import com.imd.backend.domain.entities.filmlog.MovieLike;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieLikeRepository extends BaseLikeRepository<MovieLike> {
}
