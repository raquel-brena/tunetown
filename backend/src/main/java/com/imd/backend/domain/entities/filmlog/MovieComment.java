package com.imd.backend.domain.entities.filmlog;

import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.BasePost;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "movie_comments")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MovieComment extends BaseComment {

  // PONTO VARIÁVEL:
  // Define que este comentário pertence especificamente a uma Review de Filme.
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "movie_review_id", nullable = false)
  private MovieReview post;

  @Column(name = "minute_mark")
  private Integer minuteMark;

    // Implementação do método abstrato do pai para retornar o post genérico
  @Override
  public BasePost getPost() {
    return this.post;
  }

  /**
   * Validação específica da ligação.
   * Deve ser chamada pelo Service antes de salvar.
   */
  public void validateAssociation() {
    if (this.post == null) {
      throw new IllegalArgumentException("O comentário deve pertencer a uma Review de Filme.");
    }
    // Ex: Regra de negócio - não pode comentar em review deletada
  }
}
