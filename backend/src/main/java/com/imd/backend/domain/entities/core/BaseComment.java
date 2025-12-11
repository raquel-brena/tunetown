package com.imd.backend.domain.entities.core;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * PONTO FIXO DO FRAMEWORK
 * Representa a lógica base de qualquer comentário.
 * É agnóstico quanto ao tipo de post que está sendo comentado.
 */
@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(columnDefinition = "TEXT", nullable = false)
  protected String contentText;

  // Relacionamento Fixo: Todo comentário tem um Autor (Perfil)
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "author_id", nullable = false)
  protected Profile author;

  @Column(name = "created_at", updatable = false)
  protected LocalDateTime createdAt;

  @Transient
  public abstract BasePost getPost();

    // --- MÉTODOS DE DOMÍNIO ---

  public void validateState() {
    if (this.contentText == null || this.contentText.trim().isEmpty()) {
      throw new IllegalArgumentException("O comentário não pode ser vazio.");
    }
    if (this.author == null) {
      throw new IllegalStateException("O comentário deve ter um autor.");
    }
  }

  /**
   * Permite editar o comentário, mantendo a integridade.
   */
  public void updateContent(String newContent) {
    if (newContent == null || newContent.trim().isEmpty()) {
      throw new IllegalArgumentException("O comentário não pode ser vazio.");
    }
    this.contentText = newContent;
  }

  public boolean isOwnedBy(Profile profile) {
    return this.author.equals(profile);
  }

  @PrePersist
  private void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

  // --- EQUALS & HASHCODE ---

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof BaseComment))
      return false;
    BaseComment that = (BaseComment) o;
    // Se tiver ID, usa o ID
    if (this.id != null && that.id != null) {
      return Objects.equals(id, that.id);
    }
    // Senão, usa identidade de objeto (fallback)
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
