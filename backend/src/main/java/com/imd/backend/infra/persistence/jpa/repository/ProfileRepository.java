package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {
}
