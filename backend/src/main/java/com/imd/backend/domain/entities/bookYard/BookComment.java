package com.imd.backend.domain.entities.bookYard;

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
@Table(name = "book_comments")
@Getter
@Setter
@SuperBuilder 
@NoArgsConstructor
public class BookComment extends BaseComment {

  // PONTO VARIÁVEL:
  // Define que este comentário pertence especificamente a uma Review de Livro.
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "book_review_id", nullable = false)
  private BookReview post;

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
      throw new IllegalArgumentException("O comentário deve pertencer a uma Review de Livro.");
    }
  }
}
