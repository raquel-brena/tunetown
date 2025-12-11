package com.imd.backend.domain.entities.bookYard;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "book_reviews")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BookReview extends BasePost {

  // --- DADOS DO ITEM (Achatados) ---
  @Column(name = "book_id", nullable = false)
  private String bookId;

  @Column(name = "book_title", nullable = false)
  private String bookTitle;

  @Column(name = "book_platform", nullable = false)
  private String bookPlatform; // "GoogleBooks"

  @Column(name = "book_artwork_url")
  private String bookArtworkUrl;

  // Específicos de Livro
  @Column(name = "book_author", nullable = false)
  private String bookAuthor;

  @Column(name = "book_isbn")
  private String bookIsbn;

  @Column(name = "book_page_count")
  private Integer bookPageCount;

  // --- DADOS ESPECÍFICOS ---

  @Column(name = "reading_status", nullable = false)
  private String readingStatus; // "READING", "COMPLETED", "WANT_TO_READ"  

  // --- RELACIONAMENTOS ---
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default
  @JsonIgnore
  private List<BookComment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default
  @JsonIgnore
  private List<BookLike> likes = new ArrayList<>();

  public void validateBookReview() {
    if (this.bookId == null || this.bookId.isBlank())
      throw new IllegalArgumentException("ID do livro é obrigatório");
    if (this.readingStatus == null || this.readingStatus.isBlank())
      throw new IllegalArgumentException("Status de leitura é obrigatório");    
  }

  // --- HELPERS ---
  public BookItem getBookItem() {
    return BookItem.builder()
        .id(this.bookId)
        .title(this.bookTitle)
        .platformName(this.bookPlatform)
        .author(this.bookAuthor)
        .isbn(this.bookIsbn)
        .pageCount(this.bookPageCount)
        .artworkUrl(this.bookArtworkUrl != null ? URI.create(this.bookArtworkUrl) : null)
        .build();
  }

  public void updateBookItem(BookItem item) {
    this.bookId = item.getId();
    this.bookTitle = item.getTitle();
    this.bookPlatform = item.getPlatformName();
    this.bookAuthor = item.getAuthor();
    this.bookIsbn = item.getIsbn();
    this.bookPageCount = item.getPageCount();
    this.bookArtworkUrl = item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null;
  }

  @Transient
  @JsonProperty("totalComments")
  public int getTotalCommentsCount() {
    return comments != null ? comments.size() : 0;
  }

  @Transient
  @JsonProperty("totalLikes")
  public int getTotalLikesCount() {
    return likes != null ? likes.size() : 0;
  }

  @Override
  public String getContent() {
    return String.format("Livro: %s, Autor: %s", this.bookTitle, this.bookAuthor);
  }
}
