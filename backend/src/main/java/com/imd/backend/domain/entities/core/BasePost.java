package com.imd.backend.domain.entities.core;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.experimental.SuperBuilder;

/**
 * PONTO FIXO DO FRAMEWORK
 * * Esta classe representa o conceito genérico de uma "Publicação".
 * Ela é uma @MappedSuperclass, o que significa que ela não gera uma tabela
 * própria,
 * mas seus atributos são herdados pelas tabelas das classes filhas (tuneets,
 * reviews, etc).
 */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA exige, mas protegemos
@AllArgsConstructor
public abstract class BasePost {

  @Id
  protected String id;

  // Relacionamento Fixo: Todo post tem um autor
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  protected User author;  

  @Column(columnDefinition = "TEXT", nullable = false)
  protected String textContent;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  protected LocalDateTime createdAt;  
  
  protected void validateState() {
    if (this.id == null || this.id.isBlank()) {
      // Garante identidade se não tiver sido gerada
      this.id = UUID.randomUUID().toString();
    }

    if (this.author == null) {
      throw new IllegalArgumentException("Todo post deve ter um autor.");
    }

    if (this.textContent == null || this.textContent.trim().isEmpty()) {
      throw new IllegalArgumentException("O conteúdo do texto não pode estar vazio.");
    }
  }  

  /**
   * Ação de negócio: Atualizar o texto.
   * Mantém o encapsulamento e valida a regra de negócio.
   */
  public void updateContent(String newContent) {
    if (newContent == null || newContent.trim().isEmpty()) {
      throw new IllegalArgumentException("Não é possível atualizar para um texto vazio.");
    }
    this.textContent = newContent;
    // Aqui poderíamos atualizar um campo 'updatedAt' se existisse
  }
  
  /**
   * Verifica se o usuário solicitante é o dono do post.
   * Útil para guardas de segurança.
   */
  public boolean isOwnedBy(String userId) {
    if (this.author == null || this.author.getId() == null)
      return false;
    return this.author.getId().equals(userId);
  }  

  // equals e hashCode baseados APENAS no ID (identidade)
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof BasePost))
      return false;
    BasePost that = (BasePost) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  } 

  /**
   * SAFETY NET:
   * Executa automaticamente antes de qualquer INSERT no banco.
   * Garante que, mesmo se usarem o Builder errado, o ID será gerado.
   */
  @PrePersist
  private void ensureIdAndValidation() {
      this.validateState();
  }  
}
