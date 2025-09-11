package com.imd.backend.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imd.backend.app.service.TuneetService;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/tuneet")
@RequiredArgsConstructor
public class TuneetController {
  private final TuneetService tuneetService;

  @GetMapping("/search-tunable-item")
  public ResponseEntity<List<TunableItem>> searchTunableItem(
    @RequestParam(defaultValue = "") String query,
    @RequestParam(defaultValue = "music") TunableItemType itemType
  ) {
    final List<TunableItem> items = tuneetService.searchTunableItems(query, itemType);

    return ResponseEntity.ok(items);
  }
}
