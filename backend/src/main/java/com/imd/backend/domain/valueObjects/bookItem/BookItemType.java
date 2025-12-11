package com.imd.backend.domain.valueObjects.bookItem;

public enum BookItemType {
  BOOK,
  MAGAZINE; // Deixando a porta aberta para expansão

  public static BookItemType fromString(String type) {
    return valueOf(type.toUpperCase());
  }
}
