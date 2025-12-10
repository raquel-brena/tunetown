package com.imd.backend.domain.valueObjects.movieItem;

public enum FilmItemType {
  MOVIE("MOVIE"),
  SERIES("SERIES"); // Deixando pronto para o futuro

  public static FilmItemType fromString(String type) {
    return valueOf(type.toUpperCase());
  }
}
