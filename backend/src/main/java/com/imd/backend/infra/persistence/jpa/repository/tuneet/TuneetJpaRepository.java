package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.exception.RepositoryException;
import com.imd.backend.domain.repository.TuneetRepository;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
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
  public void update(Tuneet tuneet) {
    this.save(tuneet);
  }  

  @Override
  public void deleteById(UUID id) {
    tuneetJPA.deleteById(id.toString());
  }

  @Override
  public Optional<Tuneet> findById(UUID id){
    final Optional<TuneetEntity> opEntity = this.tuneetJPA.findById(id.toString());

    try {
      if(opEntity.isPresent()) {
        final TuneetEntity entity = opEntity.get();

        return Optional.of(tuneetJpaMapper.tuneetFromJpaEntity(entity));
      }  

      return Optional.empty();
    } catch (Exception e) {
      throw new RepositoryException("Erro ao retornar dados da camada de persistência: " + e.getLocalizedMessage());
    }
  }

  @Override
  public PageResult<Tuneet> findAll(Pagination pagination) {
    final Sort.Direction direction = Sort.Direction.fromString(pagination.orderDirection());
    final Sort sort = Sort.by(direction, pagination.orderBy());
    final Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), sort);

    final var findedTuneets = this.tuneetJPA.findAll(pageable);

    return new PageResult<Tuneet>(
      findedTuneets.getContent().stream().map(entity -> tuneetJpaMapper.tuneetFromJpaEntity(entity)).toList(), 
      findedTuneets.getNumberOfElements(),
      findedTuneets.getTotalElements(),
      findedTuneets.getNumber(),
      findedTuneets.getSize(),
      findedTuneets.getTotalPages()
    );    
  }

  @Override
  public PageResult<Tuneet> findByAuthorId(String authorId, Pagination pagination) {

    final Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
        Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

    final var findedTuneets = tuneetJPA.findByAuthorId(authorId, pageable);

    return new PageResult<>(
      findedTuneets.getContent().stream()
          .map(entity -> tuneetJpaMapper.tuneetFromJpaEntity(entity))
          .toList(),
      findedTuneets.getNumberOfElements(),
      findedTuneets.getTotalElements(),
      findedTuneets.getNumber(),
      findedTuneets.getSize(),
      findedTuneets.getTotalPages()
    );
  }  

  @Override
  public PageResult<Tuneet> findByTunableItemId(String tunableItemId, Pagination pagination) {
    Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
        Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

    var findedTuneets = tuneetJPA.findByTunableItemId(tunableItemId, pageable);

    return new PageResult<>(
      findedTuneets.getContent().stream()
          .map(entity -> tuneetJpaMapper.tuneetFromJpaEntity(entity))
          .toList(),
      findedTuneets.getNumberOfElements(),
      findedTuneets.getTotalElements(),
      findedTuneets.getNumber(),
      findedTuneets.getSize(),
      findedTuneets.getTotalPages()
    );
  }  

  @Override
  public PageResult<Tuneet> findByTunableItemTitleContaining(String word, Pagination pagination) {
    Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
        Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

    var findedTuneets = tuneetJPA.findByTunableItemTitleContainingIgnoreCase(word, pageable);

    return new PageResult<>(
      findedTuneets.getContent().stream()
          .map(entity -> tuneetJpaMapper.tuneetFromJpaEntity(entity))
          .toList(),
      findedTuneets.getNumberOfElements(),
      findedTuneets.getTotalElements(),
      findedTuneets.getNumber(),
      findedTuneets.getSize(),
      findedTuneets.getTotalPages()
      );
  }  

  @Override
  public PageResult<Tuneet> findByTunableItemArtistContaining(String word, Pagination pagination) {
    Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
        Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

    var findedTuneets = tuneetJPA.findByTunableItemArtistContainingIgnoreCase(word, pageable);

    return new PageResult<>(
      findedTuneets.getContent().stream()
          .map(entity -> tuneetJpaMapper.tuneetFromJpaEntity(entity))
          .toList(),
      findedTuneets.getNumberOfElements(),
      findedTuneets.getTotalElements(),
      findedTuneets.getNumber(),
      findedTuneets.getSize(),
      findedTuneets.getTotalPages()
    );
  }

  @Override
  public List<TrendingTuneResult> findTrendingTunesByType(TunableItemType type, int limit) {
    final Pageable pageable = PageRequest.of(0, limit); // pegar top 10
    final var result = this.tuneetJPA.findTrendingTunesByType(type.toString(), pageable);

    return result.stream()
      .map(p -> new TrendingTuneResult(
        p.getItemId(),
        p.getTitle(),
        p.getArtist(),
        p.getPlatformId(),
        TunableItemType.fromString(p.getItemType()),
        p.getArtworkUrl() != null ? URI.create(p.getArtworkUrl()) : null,
        p.getTuneetCount()
      ))
      .toList();    
  }  
}
