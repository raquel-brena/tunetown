package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.exception.RepositoryException;
import com.imd.backend.domain.repository.TuneetRepository;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TuneetResume;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;
import com.imd.backend.infra.persistence.jpa.projections.TimelineItemProjection;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;

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
    final Optional<TuneetEntity> opEntity = tuneetJPA.findById(id.toString());

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
  public PageResult<Tuneet> findByAuthorId(UUID authorId, Pagination pagination) {

    final Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
        Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

     Page<TuneetEntity> findedTuneets = tuneetJPA.findByAuthorId(authorId.toString(), pageable);

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

  @Override
  public PageResult<TuneetResume> findTuneetResumeByAuthorId(UUID authorId, Pagination pagination) {
    Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
        Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));    
    
    final Page<TuneetResumeProjection> projection = this.tuneetJPA.findTuneetResumeByAuthorId(authorId.toString(), pageable);

    return new PageResult<TuneetResume>(
        projection.getContent().stream()
            .map(p -> tuneetJpaMapper.resumeFromProjection(p))
            .toList(),
        projection.getNumberOfElements(),
        projection.getTotalElements(),
        projection.getNumber(),
        projection.getSize(),
        projection.getTotalPages());
  }

  @Override
  public PageResult<TuneetResume> findAllTuneetResume(Pagination pagination) {
    Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
        Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

    final Page<TuneetResumeProjection> projection = this.tuneetJPA.findAllTuneetResume(pageable);
    
    return new PageResult<TuneetResume>(
        projection.getContent().stream()
            .map(p -> tuneetJpaMapper.resumeFromProjection(p))
            .toList(),
        projection.getNumberOfElements(),
        projection.getTotalElements(),
        projection.getNumber(),
        projection.getSize(),
        projection.getTotalPages());    
  }

  @Override
  public Optional<TuneetResume> findTuneetResumeById(UUID id) {
    final Optional<TuneetResumeProjection> op = this.tuneetJPA.findTuneetResumeById(id.toString());

    if(op.isEmpty()) return Optional.empty();
    
    return Optional.of(this.tuneetJpaMapper.resumeFromProjection(op.get()));
  }

  @Override
  public PageResult<TimeLineItem> getGlobalTimeline(Pagination pagination) {
    // Timelines são SEMPRE ordenadas por data, do mais novo para o mais antigo.
    // Ignoramos a ordenação que vem da paginação.
    Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), sort);

    Page<TimelineItemProjection> pageResult = tuneetJPA.findGlobalTimeline(pageable);

    return new PageResult<>(
        pageResult.getContent().stream()
        .map(tuneetJpaMapper::fromTimelineProjection)
        .toList(),
        pageResult.getNumberOfElements(),
        pageResult.getTotalElements(),
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalPages());    
  }

  @Override
  public PageResult<TimeLineItem> getHomeTimeline(UUID currentUserId, Pagination pagination) {
    Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), sort);

    Page<TimelineItemProjection> pageResult = tuneetJPA.findHomeTimeline(
        currentUserId.toString(),
        pageable);

    return new PageResult<>(
        pageResult.getContent().stream()
            .map(tuneetJpaMapper::fromTimelineProjection)
            .toList(),
        pageResult.getNumberOfElements(),
        pageResult.getTotalElements(),
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalPages());
  }  
}
