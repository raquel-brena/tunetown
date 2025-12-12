package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.mapper;

import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.dto.GoogleBookDetailDTO;
import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.dto.IndustryIdentifierDTO;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;
import org.springframework.stereotype.Component;

@Component
public class BookItemGoogleMapper {

  private static final String PLATFORM_NAME = "GoogleBooks";

  public BookItem fromGoogleVolume(GoogleBookDetailDTO dto) {
    var info = dto.volumeInfo();
    if (info == null)
      return null;

    String authors = "Desconhecido";
    if (info.authors() != null && !info.authors().isEmpty()) {
      authors = String.join(", ", info.authors());
    }

    String artworkUrl = null;
    if (info.imageLinks() != null) {
      artworkUrl = info.imageLinks().thumbnail();
    }

    String isbn = null;
    if (info.industryIdentifiers() != null) {
      isbn = info.industryIdentifiers().stream()
          .filter(id -> "ISBN_13".equals(id.type()))
          .map(IndustryIdentifierDTO::identifier)
          .findFirst()
          .orElse(
              !info.industryIdentifiers().isEmpty()
                  ? info.industryIdentifiers().get(0).identifier()
                  : null
          );
    }

    return new BookItem(
        dto.id(),
        PLATFORM_NAME,
        info.title(),
        artworkUrl, // Url String (o construtor converte pra URI)
        authors,
        isbn,
        info.pageCount());
  }
}
