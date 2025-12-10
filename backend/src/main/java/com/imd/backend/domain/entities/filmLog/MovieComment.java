package com.imd.backend.domain.entities.filmLog;

import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.BasePost;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
  private MovieReview movieReview;

  // Implementação do método abstrato do pai para retornar o post genérico
  @Override
  public BasePost getPost() {
    return this.movieReview;
  }

  /**
   * Validação específica da ligação.
   * Deve ser chamada pelo Service antes de salvar.
   */
  public void validateAssociation() {
    if (this.movieReview == null) {
      throw new IllegalArgumentException("O comentário deve pertencer a uma Review de Filme.");
    }
    // Ex: Regra de negócio - não pode comentar em review deletada
  }
}
