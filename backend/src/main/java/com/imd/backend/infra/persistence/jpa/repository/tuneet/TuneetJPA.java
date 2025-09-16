package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

public interface TuneetJPA extends JpaRepository<TuneetEntity, String> {
  
}
