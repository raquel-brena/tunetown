package com.imd.backend.domain.entities.tunetown;

import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.Profile;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tuneet_comments")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Comment extends BaseComment {

  // PONTO VARIÁVEL (Implementação):
  // Aqui definimos que ESTE comentário pertence a um TUNEET.
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "tuneet_id", nullable = false)
  private Tuneet post;

  /**
   * Factory Method para criar um comentário específico de Tuneet.
   */
  public static Comment create(Profile author, Tuneet tuneet, String contentText) {
    Comment comment = Comment.builder()
        .author(author)
        .post(tuneet)
        .contentText(contentText)
        // createdAt será gerado pelo PrePersist do pai
        .build();

    comment.validateState();

    if (comment.getPost() == null) {
      throw new IllegalArgumentException("O comentário deve pertencer a um Tuneet.");
    }

    return comment;
  }

    @Override
    public BasePost getPost() {
        return null;
    }
}
