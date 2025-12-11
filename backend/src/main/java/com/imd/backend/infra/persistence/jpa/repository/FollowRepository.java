package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.core.Follow;
import com.imd.backend.domain.entities.core.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowed(Profile follower, Profile followed);

    void deleteByFollowerAndFollowed(Profile follower, Profile followed);

    List<Follow> findByFollower(Profile follower);

    List<Follow> findByFollowed(Profile profile);
}

