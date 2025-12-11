package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.core.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<MediaFile, Long> {
}
