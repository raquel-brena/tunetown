package com.imd.backend.api.controller;

import com.imd.backend.app.service.TuneetService;
import com.imd.backend.domain.repository.TuneetRepository;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imd.backend.api.dto.CreateTuneetDTO;
import com.imd.backend.api.dto.UpdateTuneetDTO;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TuneetResume;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.infra.security.TuneUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/tuneet")
@RequiredArgsConstructor
public class TuneetController {
  private final TuneetService tuneetService;
  private final TuneetRepository tuneetRepository;
  private final TuneetJpaMapper tuneetJpaMapper;

    @GetMapping(path = "/{tuneetId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> getById(
    @PathVariable(required = true) UUID tuneetId
  ) {
    final Optional<Tuneet> tuneetOp = this.tuneetService.findTuneetById(String.valueOf(tuneetId));

    return ResponseEntity.of(tuneetOp);
  }
  

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageResult<Tuneet>> getAllTuneets(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "id") String orderBy,
      @RequestParam(defaultValue = "ASC") String orderDirection
  ) {
    final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);
    final PageResult<Tuneet> tuneets = this.tuneetService.findAllTuneets(pagination);

    return ResponseEntity.ok(tuneets);
  }

  @GetMapping(path = "author/{authorId}" , produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageResult<Tuneet>> getTuneetsByAuthorId(
      @PathVariable(required = true) String authorId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "id") String orderBy,
      @RequestParam(defaultValue = "ASC") String orderDirection) {
    final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);
    final PageResult<Tuneet> tuneets = this.tuneetService.findTuneetsByAuthorId(authorId, pagination);

    return ResponseEntity.ok(tuneets);
  }  

  @GetMapping(path = "tunable-item/{tunableItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageResult<Tuneet>> getTuneetsByTunableItem(
      @PathVariable(required = true) String tunableItemId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "id") String orderBy,
      @RequestParam(defaultValue = "ASC") String orderDirection) {
    final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);
    final PageResult<Tuneet> tuneets = this.tuneetService.findTuneetByTunableItem(tunableItemId, pagination);

    return ResponseEntity.ok(tuneets);
  }  

  @GetMapping(path = "tunable-item/search-by-title/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageResult<Tuneet>> getTuneetsByTunableItemTitleContaining(
      @PathVariable(required = true) String word,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "id") String orderBy,
      @RequestParam(defaultValue = "ASC") String orderDirection) {
    final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);
    final PageResult<Tuneet> tuneets = this.tuneetService.findTuneetByTunableItemTitleContaining(word, pagination);

    return ResponseEntity.ok(tuneets);
  }  

  @GetMapping(path = "tunable-item/search-by-artist/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PageResult<Tuneet>> getTuneetsByTunableItemArtistContaining(
      @PathVariable(required = true) String word,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "id") String orderBy,
      @RequestParam(defaultValue = "ASC") String orderDirection) {
    final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);
    final PageResult<Tuneet> tuneets = this.tuneetService.findTuneetByTunableItemArtistContaining(word, pagination);

    return ResponseEntity.ok(tuneets);
  }  

  @GetMapping("/trending")
  public ResponseEntity<List<TrendingTuneResult>> getTrending(
      @RequestParam(defaultValue = "music") TunableItemType type,
      @RequestParam(defaultValue = "10") int limit
  ) {
    List<TrendingTuneResult> trending = tuneetService.getTrendingTunes(type, limit);
    return ResponseEntity.ok(trending);
  }  
  

  @GetMapping("/search-tunable-item")
  public ResponseEntity<List<TunableItem>> searchTunableItem(
    @RequestParam(required = true) String query,
    @RequestParam(defaultValue = "music") TunableItemType itemType
  ) {
    final List<TunableItem> items = tuneetService.searchTunableItems(query, itemType);

    return ResponseEntity.ok(items);
  }

    @GetMapping("/resume")
    public ResponseEntity<PageResult<TuneetResumeProjection>> getAllTuneetResume(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") String orderDirection) {

        final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);

        final PageResult<TuneetResumeProjection> resumes =
                tuneetService.findAllTuneetResume(pagination);

        return ResponseEntity.ok(resumes);
    }

  @GetMapping("author/{authorId}/resume")
  public ResponseEntity<PageResult<TuneetResume>> getTuneetResumeByAuthorId(
      @PathVariable(required = true) String authorId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "createdAt") String orderBy,
      @RequestParam(defaultValue = "ASC") String orderDirection) {
    final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);
    final var resumes = this.tuneetService.findTuneetResumeByAuthorId(UUID.fromString(authorId), pagination);

    return ResponseEntity.ok(resumes);
  }  

  @GetMapping("/{id}/resume")
  public ResponseEntity<TuneetResume> getTuneetResumeById(
      @PathVariable(required = true) String id
  ) {
   this.tuneetService.findTuneetResumeById(UUID.fromString(id));

   var founded = this.tuneetRepository.findTuneetResumeById(id);
   if (founded.isEmpty()) throw new NotFoundException("Tuneet Resume não encontrado.");

   final TuneetResume op = tuneetJpaMapper.resumeFromProjection(founded.get());

    return ResponseEntity.ok(op);
  }  

  @GetMapping("/global")
  public ResponseEntity<PageResult<TimeLineItem>> getGlobalTimeline(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size
  ) {
    Pagination pagination = new Pagination(page, size, "createdAt", "DESC");
    PageResult<TimeLineItem> result = this.tuneetService.getGlobalTimeLine(pagination);
    return ResponseEntity.ok(result);
  }  

  @GetMapping("/home")
  public ResponseEntity<PageResult<TimeLineItem>> getHomeTimeline(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @AuthenticationPrincipal TuneUserDetails userDetails) {
    if (userDetails == null || userDetails.user() == null || userDetails.user().getId() == null) {
      return ResponseEntity.status(401).build();
    }

    String currentUserId = userDetails.user().getId();
    Pagination pagination = new Pagination(page, size, "createdAt", "DESC");

    PageResult<TimeLineItem> result = this.tuneetService.getHomeTimeLine(UUID.fromString(currentUserId), pagination);
    return ResponseEntity.ok(result);
  }  


  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> createTuneet(
    @Valid @RequestBody CreateTuneetDTO createTuneetDTO,
    @AuthenticationPrincipal TuneUserDetails userDetails
  ) {
    final UUID userId = UUID.fromString(userDetails.user().getId());
    final Tuneet createdTuneet = this.tuneetService.createTuneet(
      createTuneetDTO.itemId(),
      userId,
      createTuneetDTO.itemType(),
      createTuneetDTO.textContent()
    );

    return new ResponseEntity<Tuneet>(createdTuneet, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{tuneetId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> deleteTuneet(
      @PathVariable UUID tuneetId,
      @AuthenticationPrincipal TuneUserDetails loggedUser) {
    final Optional<Tuneet> tuneetOp = tuneetService.findTuneetById(String.valueOf(tuneetId));

    if(tuneetOp.isEmpty())
      throw new NotFoundException("Não foi encontrado nenhum tuneet com esse ID");

    if (!tuneetOp.get().getAuthor().getId().equals(loggedUser.user().getId()))
      throw new AccessDeniedException("Você não pode deletar um tuneet que não é seu");

    tuneetService.deleteById(tuneetId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PatchMapping(value = "/{tuneetId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> updateTuneet(
      @PathVariable UUID tuneetId,
      @Valid @RequestBody UpdateTuneetDTO updateTuneetDTO,
      @AuthenticationPrincipal TuneUserDetails loggedUser) {
    final Optional<Tuneet> tuneetOp = tuneetService.findTuneetById(String.valueOf(tuneetId));

    if (tuneetOp.isEmpty())
      throw new NotFoundException("Não foi encontrado nenhum tuneet com esse ID");

    if (!tuneetOp.get().getAuthor().getId().equals(loggedUser.user().getId()))
      throw new AccessDeniedException("Você não pode deletar um tuneet que não é seu");

    final Tuneet updated = tuneetService.updateTuneet(
        tuneetId,
        updateTuneetDTO.textContent(),
        updateTuneetDTO.itemId(),
        updateTuneetDTO.itemType());

    return ResponseEntity.ok(updated);
  }
}
