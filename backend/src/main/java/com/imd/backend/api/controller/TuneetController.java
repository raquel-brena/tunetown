package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BasePostController;
import com.imd.backend.api.dto.CreateTuneetDTO;
import com.imd.backend.api.dto.UpdateTuneetDTO;
import com.imd.backend.app.service.TuneetService;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.infra.security.CoreUserDetails;
import jakarta.validation.Valid;

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
  // OPERAÇÕES DE ESCRITA (Create / Update)
  // ==================================================================================

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> createTuneet(
      @Valid @RequestBody CreateTuneetDTO createTuneetDTO,
      @AuthenticationPrincipal CoreUserDetails userDetails) {
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
      @AuthenticationPrincipal CoreUserDetails userDetails) {
    
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
  // INTEGRAÇÃO / TRENDING / SEARCH EXTERNO
  // ==================================================================================

  @GetMapping("/search-tunable-item")
  public ResponseEntity<List<TunableItem>> searchTunableItem(
      @RequestParam String query,
      @RequestParam(defaultValue = "MUSIC", name = "type") TunableItemType itemType) {
    final List<TunableItem> items = tuneetService.searchTunableItems(query, itemType);
    return ResponseEntity.ok(items);
  }
}