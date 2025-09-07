package com.imd.backend.domain.entities.TunableItem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TunableItemType {
  MUSIC("MUSIC"),
  ALBUM("ALBUM"),
  PODCAST("PODCAST");

  private String typeName;
}
