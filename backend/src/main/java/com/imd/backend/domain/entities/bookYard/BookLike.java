package com.imd.backend.domain.entities.bookYard;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "book_likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "book_review_id", "profile_id" })
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookLike extends BaseLike {

  // PONTO VARIÁVEL: Este Like aponta para uma Review de Livro
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "book_review_id", nullable = false)
  private BookReview post;

  @Override
  public BasePost getPost() {
    return this.post;
  }

  /**
   * Validação específica.
   */
  public void validateAssociation() {
    if (this.post == null) {
      throw new IllegalArgumentException("O like deve pertencer a uma Review de Livro.");
    }
  }
}
