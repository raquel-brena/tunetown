package com.imd.backend.domain.repository;

import com.imd.backend.domain.entities.tunetown.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Page<Like> findByTuneetId(String tuneetId, Pageable pageable);

    Optional<Like> findByProfileIdAndTuneetId(String profileId, String tuneetId);
}
