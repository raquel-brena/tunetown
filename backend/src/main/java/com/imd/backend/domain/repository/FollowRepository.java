package com.imd.backend.domain.repository;

import com.imd.backend.domain.entities.core.Follow;
import com.imd.backend.domain.entities.core.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowed(Profile follower, Profile followed);

    void deleteByFollowerAndFollowed(Profile follower, Profile followed);

    List<Follow> findByFollower(Profile follower);

    List<Follow> findByFollowed(Profile profile);
}

