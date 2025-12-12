package com.imd.backend.domain.valueobjects.movieitem;

import com.imd.backend.domain.valueobjects.core.PostItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.net.URI;

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
