package com.imd.backend.domain.entities.core;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Exigido pelo JPA
public class Profile {
    @Id
    private String id;

    @Column(columnDefinition = "TEXT")
    private String bio;

    // Em um framework puro, isso seria "tagline" ou "status".
    // Mantido como favoriteSong para compatibilidade com seu código atual.
    // TODO: MUDAR ISSO
    @Column(name = "favorite_song")
    private String favoriteSong;

    // RELACIONAMENTO CORE: Perfil pertence a um Usuário
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "photo_id")
    private MediaFile photo;

    // RELACIONAMENTO SOCIAL
    // MappedBy refere-se aos campos na entidade 'Follow' (que refatoraremos em seguida)
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> followers = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Campos auxiliares para leitura (não persistidos)
    @Transient
    @Setter // Permitimos setter aqui pois geralmente é preenchido por DTO/Query
    private Long totalFollowers = 0L;

    @Transient
    @Setter
    private Long totalFollowing = 0L;

    /**
     * Cria um perfil novo vinculado a um usuário.
     */
    public static Profile create(User user, String bio, String favoriteSong) {
        Profile profile = new Profile();
        profile.id = UUID.randomUUID().toString();
        profile.user = user;
        profile.bio = bio;
        profile.favoriteSong = favoriteSong;
        profile.createdAt = LocalDateTime.now();

        profile.validateState();
        return profile;
    }    

    // --- MÉTODOS DE DOMÍNIO (Comportamento) ---

    public void updateInfo(String newBio, String newFavoriteSong) {
        this.bio = newBio;
        this.favoriteSong = newFavoriteSong;

        if (this.bio != null && this.bio.length() > 500) {
            throw new IllegalArgumentException("Bio muito longa (máx 500 caracteres).");
        }
    }

    public void changePhoto(MediaFile newPhoto) {
        this.photo = newPhoto;
    }

    public void removePhoto() {
        this.photo = null;
    }

    // --- VALIDAÇÕES E HOOKS ---

    private void validateState() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.user == null) {
            throw new IllegalStateException("Todo perfil deve pertencer a um usuário.");
        }
    }

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // --- EQUALS & HASHCODE (Identidade) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }    
}