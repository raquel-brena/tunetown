package com.imd.backend.api.dto;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

public record UpdateTuneetDTO (
  String textContent,
  String itemId,
  TunableItemType itemType
) {}
