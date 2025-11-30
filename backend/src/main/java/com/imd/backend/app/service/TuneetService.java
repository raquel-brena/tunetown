package com.imd.backend.app.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.PostItem;
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
public class TuneetService extends BasePostService<Tuneet, TunableItem> {

    private final TuneetRepository tuneetRepository;

    public TuneetService(
            @Qualifier("SpotifyGateway") TunablePlataformGateway platformGateway,
            TuneetRepository tuneetRepository,
            UserService userService,
            TuneetJpaMapper mapper
    ) {
        super(platformGateway, tuneetRepository, userService, mapper);
        this.tuneetRepository = tuneetRepository;
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
    return platformGateway.searchItem(query, itemType);
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
                      .map(mapper::resumeFromProjection)
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

    TuneetResume resume = mapper.resumeFromProjection(tuneetResume);

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
                      .map(mapper::fromTimelineProjection)
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
                      .map(mapper::fromTimelineProjection)
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

        final PostItem tunableItem = this.platformGateway.getItemById(tunableItemId, tunableItemType);
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

//    if(hasItemId && hasItemType) {
//      final TunableItem tunableItem = this.plataformGateway.getItemById(tunableItemId, tunableItemType);
//      tuneet.setTunableItemId(tunableItemId);
//      tuneet.setTunableItemArtist(tunableItem.getArtist());
//      tuneet.setTunableItemTitle(tunableItem.getTitle());
//      tuneet.setTunableItemArtworkUrl(String.valueOf(tunableItem.getArtworkUrl()));
//      tuneet.setTunableItemType(String.valueOf(tunableItem.getItemType()));
//      tuneet.setTunableItemPlataform(tunableItem.getPlataformId());
//    }

    if(hasTextContent)
      tuneet.setTextContent(textContent);
    
    this.tuneetRepository.save(tuneet);
    return tuneet;
  }

    @Override
    protected Tuneet saveEntity(Tuneet entity) {
        return null;
    }

    @Override
    protected TunableItem fetchItemById(String itemId, Object itemType) {
        return null;
    }

    @Override
    protected void applyNewItem(Tuneet entity, TunableItem item) {

    }

    @Override
    protected void applyTextUpdate(Tuneet entity, String text) {

    }

    @Override
    protected Tuneet createNewEntity(UUID userId, String text, TunableItem item) {
        return null;
    }
}
