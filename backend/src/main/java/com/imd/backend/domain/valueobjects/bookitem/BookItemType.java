package com.imd.backend.domain.valueobjects.bookitem;

public enum BookItemType {
  BOOK,
  MAGAZINE; // Deixando a porta aberta para expansão

  public static BookItemType fromString(String type) {
    return valueOf(type.toUpperCase());
  }
}
