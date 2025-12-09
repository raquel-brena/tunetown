package com.imd.backend.domain.entities.filmLog;

import com.imd.backend.domain.entities.core.BaseLike;
import com.imd.backend.domain.entities.core.BasePost;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "movie_likes", uniqueConstraints = {
    // Impedir que um usuário curta o mesmo post mais de uma vez
    @UniqueConstraint(columnNames = { "movie_review_id", "profile_id" })
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieLike extends BaseLike {

  // PONTO VARIÁVEL: Este Like aponta para uma Review de Filme
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "movie_review_id", nullable = false)
  private MovieReview post;

  @Override
  public BasePost getPost() {
    return this.post;
  }

  /**
   * Validação específica.
   */
  public void validateAssociation() {
    if (this.post == null) {
      throw new IllegalArgumentException("O like deve pertencer a uma Review de Filme.");
    }
  }
}
