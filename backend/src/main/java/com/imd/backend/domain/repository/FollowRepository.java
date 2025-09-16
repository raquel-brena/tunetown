package com.imd.backend.domain.repository;

import com.imd.backend.infra.persistence.jpa.entity.Follow;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowed(ProfileEntity follower, ProfileEntity followed);

    void deleteByFollowerAndFollowed(ProfileEntity follower, ProfileEntity followed);

    List<Follow> findByFollower(ProfileEntity follower);

    List<Follow> findByFollowed(ProfileEntity profile);
}

