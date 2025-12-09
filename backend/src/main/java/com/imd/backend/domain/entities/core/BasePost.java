package com.imd.backend.domain.entities.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import jakarta.persistence.*;
import lombok.*;

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

    // Removemos @CreationTimestamp para ter controle total no Factory Method
    // Mas mantemos updatable = false para segurança
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    // --- MÉTODOS DE DOMÍNIO ---

    protected void validateState() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.author == null) {
            throw new IllegalArgumentException("Todo post deve ter um autor.");
        }
        if (this.textContent == null || this.textContent.trim().isEmpty()) {
            throw new IllegalArgumentException("O conteúdo do texto não pode estar vazio.");
        }
    }

    public void updateContent(String newContent) {
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Não é possível atualizar para um texto vazio.");
        }
        this.textContent = newContent;
    }

    public boolean isOwnedBy(String userId) {
        if (this.author == null || this.author.getId() == null)
            return false;
        return this.author.getId().equals(userId);
    }

    // --- HOOKS ---
    @PrePersist
    private void prePersist() {
        if (this.id == null)
            this.id = UUID.randomUUID().toString();
        if (this.createdAt == null)
            this.createdAt = LocalDateTime.now();
    }

    // --- EQUALS & HASHCODE ---
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BasePost))
            return false;
        BasePost basePost = (BasePost) o;
        return Objects.equals(id, basePost.id);
    }

    public abstract String getContent();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
