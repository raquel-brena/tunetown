package com.imd.backend.domain.valueObjects.movieItem;

import java.net.URI;

import com.imd.backend.domain.valueObjects.core.PostItem;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class MovieItem extends PostItem {
  private String director;
  private String releaseYear;
  private FilmItemType itemType;

  public MovieItem(
      String id,
      String platformName,
      String title,
      String artworkUrlStr,
      String director,
      String releaseYear,
      FilmItemType itemType
  ) {
    // Chama o construtor do Pai (PostItem) convertendo a URL
    super(id, title, platformName, artworkUrlStr != null ? URI.create(artworkUrlStr) : null);

    this.director = director;
    this.releaseYear = releaseYear;
    this.itemType = itemType;
  }

  // Construtor JPA-Compliant (String-based)
  public MovieItem(
      String id,
      String platformName,
      String title,
      String artworkUrlStr,
      String director,
      String releaseYear,
      String itemTypeStr // <--- NOVO ARGUMENTO NO FINAL
  ) {
    super(id, title, platformName, artworkUrlStr != null ? URI.create(artworkUrlStr) : null);
    this.director = director;
    this.releaseYear = releaseYear;
    this.itemType = itemTypeStr != null ? FilmItemType.fromString(itemTypeStr) : FilmItemType.MOVIE;
  }
}
