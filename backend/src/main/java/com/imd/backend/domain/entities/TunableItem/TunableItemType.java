package com.imd.backend.domain.entities.TunableItem;

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
}
