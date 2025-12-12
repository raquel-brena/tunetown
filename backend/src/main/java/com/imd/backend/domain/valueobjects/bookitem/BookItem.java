package com.imd.backend.domain.valueobjects.bookitem;

import com.imd.backend.domain.valueobjects.core.PostItem;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.net.URI;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class BookItem extends PostItem {
  private String author;
  private String isbn;
  private Integer pageCount;

  // Construtor JPA-Compliant
  public BookItem(
      String id,
      String platformName,
      String title,
      String artworkUrlStr,
      String author,
      String isbn,
      Integer pageCount) {
    super(id, title, platformName, artworkUrlStr != null ? URI.create(artworkUrlStr) : null);
    this.author = author;
    this.isbn = isbn;
    this.pageCount = pageCount;
  }
}
