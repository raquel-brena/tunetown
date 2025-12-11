package com.imd.backend.domain.entities.bookyard;

import com.imd.backend.domain.entities.core.BaseLike;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueobjects.bookitem.ImpactLevel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

  @Enumerated(EnumType.STRING)
  @Column(name = "impact_level", nullable = false)
  private ImpactLevel impactLevel;

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
