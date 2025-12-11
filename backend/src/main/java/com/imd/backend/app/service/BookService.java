package com.imd.backend.app.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.imd.backend.app.dto.bookYard.CreateBookReviewDTO;
import com.imd.backend.app.dto.bookYard.UpdateBookReviewDTO;
import com.imd.backend.app.gateway.bookplatformgateway.BookPlatformGateway;
import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;
import com.imd.backend.domain.valueobjects.core.BaseResume;
import com.imd.backend.infra.persistence.jpa.repository.BookRepository;

@Service
public class BookService extends BasePostService<
  BookReview,
  BookItem,
  CreateBookReviewDTO,
  UpdateBookReviewDTO
> {
  private final FileService fileService;

  public BookService(
    @Qualifier("BookJpaRepository") BookRepository repository,
    UserService userService,
    @Qualifier("GoogleBooksGateway") BookPlatformGateway itemGateway,
    FileService fileService
  ) {
    super(repository, userService, itemGateway);
    this.fileService = fileService;
  }

  @Override
  protected BookReview createEntityInstance(User author, CreateBookReviewDTO dto, BookItem item) {
    return BookReview.builder()
      // Dados Base
      .id(UUID.randomUUID().toString())
      .author(author)
      .textContent(dto.getTextContent())
      .createdAt(LocalDateTime.now())

      // Dados do Item (Google Books)
      .bookId(item.getId())
      .bookPlatform(item.getPlatformName())
      .bookTitle(item.getTitle())
      .bookAuthor(item.getAuthor())
      .bookIsbn(item.getIsbn())
      .bookPageCount(item.getPageCount())
      .bookArtworkUrl(item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null)

      // Dados Específicos (Rating removido)
      .readingStatus(dto.getReadingStatus())
      .build();
  }

  @Override
  protected void updateEntityInstance(BookReview entity, UpdateBookReviewDTO dto, BookItem newItem) {
    if (newItem != null) {
      entity.updateBookItem(newItem);
    }

    if (dto.getReadingStatus() != null) {
      entity.setReadingStatus(dto.getReadingStatus());
    }
  }

  @Override
  protected void validateSpecificEntity(BookReview entity) {
    entity.validateBookReview();
  }

  @Override
  protected void postProcessResume(BaseResume<BookItem> resume) {
    if (resume.getAuthorPhotoFileName() != null && !resume.getAuthorPhotoFileName().isBlank()) {
      String presignedUrl = fileService.applyPresignedUrl(resume.getAuthorPhotoFileName());
      resume.setAuthorPhotoUrl(presignedUrl);
    }
  }  
}
