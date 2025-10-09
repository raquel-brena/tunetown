package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TuneetResume;
import com.imd.backend.domain.exception.RepositoryException;
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

  @Override
  public void update(TuneetResume tuneetResume) {
    final TuneetEntity entityToUpdate = tuneetJpaMapper.fromTuneetResumeDomain(tuneetResume);

    this.tuneetJPA.save(entityToUpdate);
  }  

  @Override
  public void deleteById(String id) {
    tuneetJPA.deleteById(id);
  }

  @Override
  public Optional<TuneetResume> findById(String id){
    final Optional<TuneetEntity> opEntity = this.tuneetJPA.findById(id);

    try {
      if(opEntity.isPresent()) {
        final TuneetEntity entity = opEntity.get();
        System.out.println("DEBUG: Entity encontrada - ID: " + entity.getId() + ", AuthorID: " + entity.getAuthorId());

        return Optional.of(tuneetJpaMapper.resumeFromTuneetJpaEntity(entity));
      }  

      return Optional.empty();
    } catch (Exception e) {
      System.err.println("DEBUG: Erro no mapeamento: " + e.getMessage());
      e.printStackTrace();
      throw new RepositoryException("Erro ao retornar dados da camada de persistência: " + e.getLocalizedMessage());
    }
  }
}
