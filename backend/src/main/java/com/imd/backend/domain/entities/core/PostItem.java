package com.imd.backend.domain.entities.core;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import jakarta.persistence.*;
import lombok.*;

import java.net.URI;

@Entity
@Table(name = "post_items")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Exigido pelo JPA
public abstract class PostItem {

    @Id
    private String id;

    @Column(name = "plataform", nullable = false)
    private String plataform;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "artwork_url")
    private String artworkUrl;

    public void validateTunableItem() {
        if (this.id == null)
            throw new IllegalArgumentException("O item tunetável deve ter um ID.");
        if (this.title == null || this.title.isBlank())
            throw new IllegalArgumentException("O item tunetável deve ter um título.");
    }

    /**
     * Reconstrói o Value Object se necessário para uso na aplicação.
     */
    public com.imd.backend.domain.valueObjects.TunableItem.TunableItem getTunableItem() {
        return new com.imd.backend.domain.valueObjects.TunableItem.TunableItem(
                this.id,
                this.plataform,
                this.title,
                this.author,
                this.artworkUrl != null ? URI.create(this.artworkUrl) : null,
                TunableItemType.fromString(this.type));
    }

    /**
     * Atualiza os dados do item tunetável (ex: se mudar a URL da capa).
     */
    public void updateTunableItem(com.imd.backend.domain.valueObjects.TunableItem.TunableItem newItem) {
        if (newItem == null)
            throw new IllegalArgumentException("Item não pode ser nulo");

        this.id = newItem.getId();
        this.plataform = newItem.getPlataformId();
        this.title = newItem.getTitle();
        this.author = newItem.getArtist();
        this.type = newItem.getItemType().toString();
        this.artworkUrl = newItem.getArtworkUrl() != null ? newItem.getArtworkUrl().toString() : null;
    }
}
