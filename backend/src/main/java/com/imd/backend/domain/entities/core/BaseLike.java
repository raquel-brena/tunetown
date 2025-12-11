package com.imd.backend.domain.entities.core;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * PONTO FIXO DO FRAMEWORK
 * Representa a estrutura base de um "Like".
 * Define QUEM curtiu e QUANDO, mas deixa o "O QUÊ" para a subclasse.
 */
@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  // PONTO FIXO: Todo like vem de um Perfil
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "profile_id", nullable = false)
  protected Profile profile;

  @Column(name = "created_at", updatable = false)
  protected LocalDateTime createdAt;

  @Transient
  public abstract BasePost getPost();

  // --- VALIDAÇÕES BASE ---
  protected void validateState() {
    if (this.profile == null) {
      throw new IllegalArgumentException("É necessário um perfil para curtir.");
    }
  }

  @PrePersist
  private void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

  // --- EQUALS & HASHCODE (Baseado em ID) ---

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof BaseLike))
      return false;
    BaseLike other = (BaseLike) o;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
