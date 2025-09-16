package com.imd.backend.api.dto;

import com.imd.backend.domain.entities.TunableItem.TunableItemType;

public record UpdateTuneetDTO (
  String textContent,
  String itemId,
  TunableItemType itemType
) {}
