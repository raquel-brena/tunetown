package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import org.springframework.stereotype.Repository;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.repository.TuneetRepository;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;

import lombok.RequiredArgsConstructor;

@Repository("TuneetJpaRepository")
@RequiredArgsConstructor
public class TuneetJpaRepository implements TuneetRepository {
  private final TuneetJPA tuneetJPA;
  private final TuneetJpaMapper tuneetJpaMapper;

  @Override
  public void save(Tuneet tuneet) {
    final TuneetEntity entityToSave = tuneetJpaMapper.fromTuneetDomain(tuneet);

    this.tuneetJPA.save(entityToSave);
  }
  
}
