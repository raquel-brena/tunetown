package com.imd.backend.app.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.exception.RepositoryException;
import com.imd.backend.domain.repository.TuneetRepository;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;
import com.imd.backend.infra.persistence.jpa.projections.TimelineItemProjection;
import com.imd.backend.infra.persistence.jpa.projections.TrendingTuneProjection;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TuneetResume;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

import jakarta.transaction.Transactional;

@Service
public class TuneetService {
  private final TunablePlataformGateway plataformGateway;
  private final TuneetRepository tuneetRepository;
  private final UserService userService;
  private final TuneetJpaMapper tuneetJpaMapper;

  public TuneetService(
    @Qualifier("SpotifyGateway") TunablePlataformGateway plataformGateway,
    TuneetRepository tuneetRepository,
    UserService userService,
    TuneetJpaMapper tuneetJpaMapper,
    ProfileService profileService) {
    this.plataformGateway = plataformGateway;
    this.tuneetRepository = tuneetRepository;
    this.tuneetJpaMapper = tuneetJpaMapper;
    this.userService = userService;
  }

  public Optional<Tuneet> findTuneetById(String id) {
    return this.tuneetRepository.findById(id);
  }

    public PageResult<Tuneet> findAllTuneets(Pagination pagination) {
        final Sort.Direction direction = Sort.Direction.fromString(pagination.orderDirection());
        final Sort sort = Sort.by(direction, pagination.orderBy());
        final Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), sort);

        final var findedTuneets = this.tuneetRepository.findAll(pageable);

        return new PageResult<>(
                findedTuneets.getContent()
                        .stream()
                        .toList(),

                findedTuneets.getNumberOfElements(),
                findedTuneets.getTotalElements(),
                findedTuneets.getNumber(),
                findedTuneets.getSize(),
                findedTuneets.getTotalPages()
        );
    }


  public PageResult<Tuneet> findTuneetsByAuthorId(String authorId, Pagination pagination) {
      final Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
              Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

      Page<Tuneet> findedTuneets = tuneetRepository.findByAuthorId(authorId.toString(), pageable);

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
//
//      Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
//              Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));
//
//      var findedTuneets = this.tuneetRepository.findByTunableItemId(tunableItemId, pageable);
//
//      return new PageResult<>(
//              findedTuneets.getContent().stream()
//                      .map(entity -> tuneetJpaMapper.tuneetFromJpaEntity(entity))
//                      .toList(),
//              findedTuneets.getNumberOfElements(),
//              findedTuneets.getTotalElements(),
//              findedTuneets.getNumber(),
//              findedTuneets.getSize(),
//              findedTuneets.getTotalPages()
//      );
  }

  public PageResult<Tuneet> findTuneetByTunableItem(
    String tunableItemId,
    Pagination pagination
  ) {
      Pageable pageable = PageRequest.of(
              pagination.page(),
              pagination.size(),
              Sort.by(
                      Sort.Direction.fromString(pagination.orderDirection()),
                      pagination.orderBy()
              )
      );

      Page<Tuneet> page = this.tuneetRepository.findByTunableItemId(tunableItemId, pageable);
          return new PageResult<>(
              page.getContent(),
              page.getNumberOfElements(),
              page.getTotalElements(),
              page.getNumber(),
              page.getSize(),
              page.getTotalPages()
      );
  }

  public PageResult<Tuneet> findTuneetByTunableItemTitleContaining(String word, Pagination pagination) {
      Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
              Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));
      Page<Tuneet> page = this.tuneetRepository.findByTunableItemTitleContainingIgnoreCase(word, pageable);
      return new PageResult<>(
              page.getContent(),
              page.getNumberOfElements(),
              page.getTotalElements(),
              page.getNumber(),
              page.getSize(),
              page.getTotalPages()
      );
  }

  public PageResult<Tuneet> findTuneetByTunableItemArtistContaining(String word, Pagination pagination) {
      Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
              Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));
      Page<Tuneet> page =  this.tuneetRepository.findByTunableItemArtistContainingIgnoreCase(word, pageable);
      return new PageResult<>(
              page.getContent(),
              page.getNumberOfElements(),
              page.getTotalElements(),
              page.getNumber(),
              page.getSize(),
              page.getTotalPages()
      );
  }  

  public List<TunableItem> searchTunableItems(String query, TunableItemType itemType) {
    return plataformGateway.searchItem(query, itemType);
  }

    public List<TrendingTuneResult> getTrendingTunes(TunableItemType type, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        Page<TrendingTuneProjection> result =
                tuneetRepository.findTrendingTunesByTunableItemType(type.toString(), pageable);

        return result.getContent().stream()
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


    public PageResult<TuneetResumeProjection> findAllTuneetResume(Pagination pagination) {

        Pageable pageable = PageRequest.of(
                pagination.page(),
                pagination.size(),
                Sort.by(
                        Sort.Direction.fromString(pagination.orderDirection()),
                        pagination.orderBy()
                )
        );

        Page<TuneetResumeProjection> page =  tuneetRepository.findAllTuneetResume(pageable);

        return new PageResult<>(
                page.getContent(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages()
        );
    }

  public PageResult<TuneetResume> findTuneetResumeByAuthorId(UUID authorId, Pagination pagination) {
      Pageable pageable = PageRequest.of(pagination.page(), pagination.size(),
              Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

      final Page<TuneetResumeProjection> projection =
              this.tuneetRepository.findTuneetResumeByAuthorId(authorId.toString(), pageable);

      final var result = new PageResult<TuneetResume>(
              projection.getContent().stream()
                      .map(tuneetJpaMapper::resumeFromProjection)
                      .toList(),
              projection.getNumberOfElements(),
              projection.getTotalElements(),
              projection.getNumber(),
              projection.getSize(),
              projection.getTotalPages()
      );

    result.itens().forEach(t -> {
      if (t.getFileNamePhoto() == null || t.getFileNamePhoto().isBlank())
        return;

      final String presignedUrl = FileService.applyPresignedUrl(t.getFileNamePhoto());
      t.setUrlPhoto(presignedUrl);
    });

    return result;    
  }

  public TuneetResume findTuneetResumeById(UUID id) {
    final Optional<TuneetResumeProjection> op = this.tuneetRepository.findTuneetResumeById(String.valueOf(id));

    if(op.isEmpty()) throw new NotFoundException("Nenhum tuneet foi encontrado com esse ID");
    
    final TuneetResumeProjection tuneetResume = op.get();

    TuneetResume resume = tuneetJpaMapper.resumeFromProjection(tuneetResume);

    if (tuneetResume.getFileNamePhoto() != null) {
      final String presignedUrl = FileService.applyPresignedUrl(tuneetResume.getFileNamePhoto());
        resume.setUrlPhoto(presignedUrl);
    }


    return resume;
  }

  public PageResult<TimeLineItem> getGlobalTimeLine(Pagination pagination) {
      // Timelines são SEMPRE ordenadas por data, do mais novo para o mais antigo.
      // Ignoramos a ordenação que vem da paginação.
      Pageable pageable = PageRequest.of(
              pagination.page(),
              pagination.size(),
              Sort.by(
                      Sort.Direction.fromString(pagination.orderDirection()),
                      pagination.orderBy()
              )
      );

      Page<TimelineItemProjection> pageResult = tuneetRepository.findGlobalTimeline(pageable);

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

  public PageResult<TimeLineItem> getHomeTimeLine(UUID userId, Pagination pagination) {
      Pageable pageable = PageRequest.of(
              pagination.page(),
              pagination.size(),
              Sort.by(
                      Sort.Direction.fromString(pagination.orderDirection()),
                      pagination.orderBy()
              )
      );

      Page<TimelineItemProjection> pageResult = tuneetRepository.findHomeTimeline(
              userId.toString(),
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


  @Transactional(rollbackOn = Exception.class)
  public Tuneet createTuneet(
    String tunableItemId,
    UUID userId,
    TunableItemType tunableItemType,
    String textContent
  ) {
      Optional<User> user = this.userService.findUserById(userId);
        if(user.isEmpty())
          throw new BusinessException("Não existe nenhum usuário com esse ID");

        final TunableItem tunableItem = this.plataformGateway.getItemById(tunableItemId, tunableItemType);
        final Tuneet tuneetToSave = Tuneet.create(user.get(), textContent, tunableItem);
        this.tuneetRepository.save(tuneetToSave);
        return tuneetToSave;
  }

  @Transactional(rollbackOn = Exception.class)
  public Tuneet deleteById(UUID tuneetId) {
    final Optional<Tuneet> findedTuneet = this.tuneetRepository.findById(tuneetId.toString());

    if(findedTuneet.isEmpty())
      throw new NotFoundException("Não foi encontrado nenhum tuneet com esse ID");
    
    this.tuneetRepository.deleteById(tuneetId.toString());
    return findedTuneet.get();
  }

  @Transactional(rollbackOn = Exception.class)
  public Tuneet updateTuneet(
    UUID tuneetId,
    String textContent,
    String tunableItemId,
    TunableItemType tunableItemType
  ) {
    final Tuneet tuneet = this.tuneetRepository.findById(String.valueOf(tuneetId))
      .orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum tuneet com o ID fornecido"));
    
    final boolean hasTextContent = textContent != null && !textContent.isBlank();
    final boolean hasItemId = tunableItemId != null && !tunableItemId.isEmpty();
    final boolean hasItemType = tunableItemType != null;

    // Se tiver o tipo e o ID do item, mas não ambos juntos
    if(hasItemId ^ hasItemType) 
      throw new BusinessException("Para atualizar o item, é necessário passar o ID e o tipo dele");

    if(hasItemId && hasItemType) {
      final TunableItem tunableItem = this.plataformGateway.getItemById(tunableItemId, tunableItemType);
      tuneet.setTunableItemId(tunableItemId);
      tuneet.setTunableItemArtist(tunableItem.getArtist());
      tuneet.setTunableItemTitle(tunableItem.getTitle());
      tuneet.setTunableItemArtworkUrl(String.valueOf(tunableItem.getArtworkUrl()));
      tuneet.setTunableItemType(String.valueOf(tunableItem.getItemType()));
      tuneet.setTunableItemPlataform(tunableItem.getPlataformId());
    }

    if(hasTextContent)
      tuneet.setTextContent(textContent);
    
    this.tuneetRepository.save(tuneet);
    return tuneet;
  }

}
