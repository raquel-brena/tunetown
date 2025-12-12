package com.imd.backend.domain.entities.tunetown;

import com.imd.backend.domain.entities.core.BaseLike;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "likes", uniqueConstraints = {
    // PONTO VARIÁVEL: A unicidade é entre Perfil + TUNEET
    @UniqueConstraint(columnNames = { "tuneet_id", "profile_id" })
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Like extends BaseLike {

  // PONTO VARIÁVEL: Este Like aponta para um Tuneet
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "tuneet_id", nullable = false)
  private Tuneet post;

    @Override
    public BasePost getPost() {
        return post;
    }
  /**
   * Factory Method
   */
  public static Like create(Tuneet tuneet, Profile profile) {
    Like like = Like.builder()
        .post(tuneet)
        .profile(profile)
        // createdAt gerado pelo pai
        .build();

    like.validateState(); // Valida o profile (pai)

    // Validação específica do filho
    if (like.getPost() == null) {
      throw new IllegalArgumentException("O like deve pertencer a um Tuneet.");
    }

    return like;
  }
}