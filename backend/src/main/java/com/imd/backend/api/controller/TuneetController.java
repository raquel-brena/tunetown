package com.imd.backend.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imd.backend.api.dto.CreateTuneetDTO;
import com.imd.backend.app.service.TuneetService;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TuneetResume;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("tuneet")
@RequiredArgsConstructor
public class TuneetController {
  private final TuneetService tuneetService;

  @GetMapping("/search-tunable-item")
  public ResponseEntity<List<TunableItem>> searchTunableItem(
    @RequestParam(required = true) String query,
    @RequestParam(defaultValue = "music") TunableItemType itemType
  ) {
    final List<TunableItem> items = tuneetService.searchTunableItems(query, itemType);

    return ResponseEntity.ok(items);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> createTuneet(
    @RequestBody CreateTuneetDTO createTuneetDTO
  ) {
      final Tuneet createdTuneet = this.tuneetService.createTuneet(
        createTuneetDTO.itemId(),
        createTuneetDTO.itemType(),
        createTuneetDTO.textContent()
      );

      return new ResponseEntity<Tuneet>(createdTuneet, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{tuneetId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TuneetResume> deleteTuneet(
    @PathVariable(required = true) String tuneetId
  ) {
    final TuneetResume deletedTuneet = this.tuneetService.deleteById(UUID.fromString(tuneetId));

    return ResponseEntity.ok(deletedTuneet);
  }
  
}
