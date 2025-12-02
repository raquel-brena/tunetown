package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BasePostController;
import com.imd.backend.api.dto.CreateTuneetDTO;
import com.imd.backend.api.dto.UpdateTuneetDTO;
import com.imd.backend.app.service.TuneetService;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.domain.valueObjects.TuneetResume;
import com.imd.backend.infra.security.TuneUserDetails;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/tuneet")
public class TuneetController extends BasePostController<Tuneet, TunableItem> {

  private final TuneetService tuneetService;

  public TuneetController(TuneetService tuneetService) {
    super(tuneetService);
    this.tuneetService = tuneetService;
  }

  // ==================================================================================
  // MÉTODOS HERDADOS DO BASE
  // ==================================================================================
  // GET /api/tuneet (findAll)
  // GET /api/tuneet/{id} (findById)
  // GET /api/tuneet/author/{authorId} (findByAuthorId)

  // ==================================================================================
  // SOBRESCRITA DE MÉTODOS (Para regras de segurança específicas)
  // ==================================================================================

  @DeleteMapping(value = "/{tuneetId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(
      @PathVariable UUID tuneetId,
      @AuthenticationPrincipal TuneUserDetails userDetails) {
    service.deleteById(tuneetId, UUID.fromString(userDetails.user().getId()));
    return ResponseEntity.noContent().build();
  }

  // ==================================================================================
  // OPERAÇÕES DE ESCRITA (Create / Update)
  // ==================================================================================

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> createTuneet(
      @Valid @RequestBody CreateTuneetDTO createTuneetDTO,
      @AuthenticationPrincipal TuneUserDetails userDetails) {
    // O ID do usuário vem do Token JWT (UserDetails)
    final String userId = userDetails.user().getId();

    // Chama o Template Method 'create' do BasePostService (ou TuneetService)
    final Tuneet createdTuneet = this.tuneetService.create(
        userId,
        createTuneetDTO.textContent(),
        createTuneetDTO.itemId(),
        createTuneetDTO.itemType().toString());

    return new ResponseEntity<>(createdTuneet, HttpStatus.CREATED);
  }

  @PatchMapping(value = "/{tuneetId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> updateTuneet(
      @PathVariable UUID tuneetId,
      @Valid @RequestBody UpdateTuneetDTO updateTuneetDTO,
      @AuthenticationPrincipal TuneUserDetails userDetails) {
    
    final Tuneet existing = tuneetService.findById(tuneetId);

    // Verifica propriedade
    if (!existing.isOwnedBy(userDetails.user().getId())) {
      throw new AccessDeniedException("Você não pode editar um tuneet que não é seu");
    }

    final Tuneet updated = tuneetService.updateTuneet(
        tuneetId,
        updateTuneetDTO.textContent(),
        updateTuneetDTO.itemId(),
        updateTuneetDTO.itemType());

    return ResponseEntity.ok(updated);
  }

  // ==================================================================================
  // BUSCAS ESPECÍFICAS DE MÚSICA (Filtros)
  // ==================================================================================

  @GetMapping(path = "tunable-item/{tunableItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Tuneet>> getTuneetsByTunableItem(
      @PathVariable String tunableItemId,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(this.tuneetService.findTuneetByTunableItem(tunableItemId, pageable));
  }

  @GetMapping(path = "tunable-item/search-by-title/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Tuneet>> getTuneetsByTunableItemTitleContaining(
      @PathVariable String word,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(this.tuneetService.findTuneetByTunableItemTitleContaining(word, pageable));
  }

  @GetMapping(path = "tunable-item/search-by-artist/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<Tuneet>> getTuneetsByTunableItemArtistContaining(
      @PathVariable String word,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(this.tuneetService.findTuneetByTunableItemArtistContaining(word, pageable));
  }

  // ==================================================================================
  // INTEGRAÇÃO / TRENDING / SEARCH EXTERNO
  // ==================================================================================

  @GetMapping("/trending")
  public ResponseEntity<List<TrendingTuneResult>> getTrending(
      @RequestParam(defaultValue = "MUSIC") TunableItemType type, 
      @RequestParam(defaultValue = "10") int limit) {
    List<TrendingTuneResult> trending = tuneetService.getTrendingTunes(type, limit);
    return ResponseEntity.ok(trending);
  }

  @GetMapping("/search-tunable-item")
  public ResponseEntity<List<TunableItem>> searchTunableItem(
      @RequestParam String query,
      @RequestParam(defaultValue = "MUSIC", name = "type") TunableItemType itemType) {
    final List<TunableItem> items = tuneetService.searchTunableItems(query, itemType);
    return ResponseEntity.ok(items);
  }

  // ==================================================================================
  // RESUMES (DTOs Otimizados)
  // ==================================================================================

  @GetMapping("/resume")
  public ResponseEntity<Page<TuneetResume>> getAllTuneetResume(
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(tuneetService.findAllTuneetResume(pageable));
  }

  @GetMapping("author/{authorId}/resume")
  public ResponseEntity<Page<TuneetResume>> getTuneetResumeByAuthorId(
      @PathVariable UUID authorId,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(tuneetService.findTuneetResumeByAuthorId(authorId, pageable));
  }

  @GetMapping("/{id}/resume")
  public ResponseEntity<TuneetResume> getTuneetResumeById(@PathVariable UUID id) {
    // O service já lança NotFoundException se não achar
    return ResponseEntity.ok(tuneetService.findTuneetResumeById(id));
  }

  // ==================================================================================
  // TIMELINES
  // ==================================================================================

  @GetMapping("/global")
  public ResponseEntity<Page<TimeLineItem>> getGlobalTimeline(
      @PageableDefault(size = 20) Pageable pageable // Sort é forçado no service
  ) {
    return ResponseEntity.ok(this.tuneetService.getGlobalTimeLine(pageable));
  }

  @GetMapping("/home")
  public ResponseEntity<Page<TimeLineItem>> getHomeTimeline(
      @PageableDefault(size = 20) Pageable pageable,
      @AuthenticationPrincipal TuneUserDetails userDetails) {
    if (userDetails == null || userDetails.user() == null) {
      return ResponseEntity.status(401).build();
    }

    UUID currentUserId = UUID.fromString(userDetails.user().getId());
    return ResponseEntity.ok(this.tuneetService.getHomeTimeLine(currentUserId, pageable));
  }
}