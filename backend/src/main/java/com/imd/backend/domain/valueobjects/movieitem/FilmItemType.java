package com.imd.backend.domain.valueobjects.movieitem;

public enum FilmItemType {
  MOVIE,
  SERIES;

  public static FilmItemType fromString(String type) {
    return valueOf(type.toUpperCase());
  }
}
