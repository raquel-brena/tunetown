package com.imd.backend.domain.valueObjects.TunableItem;

import com.imd.backend.domain.exception.BusinessException;

public enum TunableItemType {
  MUSIC("MUSIC"),
  ALBUM("ALBUM"),
  PODCAST("PODCAST");

  private String typeName;

  TunableItemType(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return this.typeName;
  }

  public static TunableItemType fromString(String typeName) {
    switch (typeName.toLowerCase()) {
      case "music":
        return MUSIC;    
      case "album":
        return ALBUM;
      case "podcast":
        return PODCAST;
      default:
        throw new BusinessException("Tipo inválido de item tunetável");
    }
  }

  public String toString() {
    return this.typeName.toLowerCase();
  }
}
