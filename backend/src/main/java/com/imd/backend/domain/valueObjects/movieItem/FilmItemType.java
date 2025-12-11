package com.imd.backend.domain.valueObjects.movieItem;

public enum FilmItemType {
  MOVIE,
  SERIES;

  public static FilmItemType fromString(String type) {
    return valueOf(type.toUpperCase());
  }
}
