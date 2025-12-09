package com.imd.backend.domain.valueObjects.movieItem;

import java.net.URI;

import com.imd.backend.domain.valueObjects.core.PostItem;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class MovieItem extends PostItem {

  private String director;
  private String releaseYear; // String para evitar problemas de formatação
  // Poderia ter: private String genre;

  // Construtor JPA-Compliant (String-based)
  public MovieItem(
      String id,
      String platformName,
      String title,
      String artworkUrlStr,
      String director,
      String releaseYear) {
    super(id, title, platformName, artworkUrlStr != null ? URI.create(artworkUrlStr) : null);
    this.director = director;
    this.releaseYear = releaseYear;
  }
}
