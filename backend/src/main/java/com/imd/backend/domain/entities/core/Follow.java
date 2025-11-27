package com.imd.backend.domain.entities.core;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "follows", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "follower_id", "followed_id" })
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Exigido pelo JPA
public class Follow {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Quem está seguindo
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "follower_id", nullable = false)
  private Profile follower;

  // Quem está sendo seguido
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "followed_id", nullable = false)
  private Profile followed;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  private Follow(Profile follower, Profile followed) {
    this.follower = follower;
    this.followed = followed;
    this.validateState();
  }

  public static Follow create(Profile follower, Profile followed) {
    Follow follow = new Follow(follower, followed);
    // O ID será gerado pelo Banco (IDENTITY), então não setamos aqui
    // O createdAt será setado pelo @PrePersist
    return follow;
  }

  // --- VALIDAÇÕES E HOOKS ---

  private void validateState() {
    if (this.follower == null) {
      throw new IllegalArgumentException("O seguidor (follower) não pode ser nulo.");
    }
    if (this.followed == null) {
      throw new IllegalArgumentException("O seguido (followed) não pode ser nulo.");
    }
    // Regra de Negócio: Auto-follow proibido
    if (this.follower.equals(this.followed)) {
      throw new IllegalArgumentException("Um perfil não pode seguir a si mesmo.");
    }
  }

  @PrePersist
  private void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

  // --- EQUALS & HASHCODE ---
  // Para entidades com ID gerado no banco (Identity), o equals é complicado antes
  // de salvar.
  // Uma boa prática para entidades associativas é usar a chave composta de
  // negócio (follower + followed).

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Follow follow = (Follow) o;
    // Se ambos tiverem ID, usa o ID
    if (id != null && follow.id != null) {
      return Objects.equals(id, follow.id);
    }
    // Se não (ainda não persistido), usa a chave de negócio (quem segue quem)
    return Objects.equals(follower, follow.follower) &&
        Objects.equals(followed, follow.followed);
  }

  @Override
  public int hashCode() {
    // Se tiver ID, usa o hash do ID, senão usa o hash dos perfis
    if (id != null)
      return Objects.hash(id);
    return Objects.hash(follower, followed);
  }
}
