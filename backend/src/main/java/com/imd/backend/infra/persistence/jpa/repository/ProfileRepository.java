package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.core.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    Optional<Profile> findByUserUsername(String username);
}
