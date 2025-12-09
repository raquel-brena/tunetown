package com.imd.backend.domain.entities.tunetown;

import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tuneets")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tuneet extends BasePost {

    // --- CAMPOS ESPECÍFICOS (VARIÁVEIS) ---
    // Estes campos representam o "Item" específico desta instância do framework.
    // Eles estão "achatados" na tabela para facilitar queries.
    @Column(name = "tunable_item_id", nullable = false)
    private String tunableItemId;

    @Column(name = "tunable_item_plataform", nullable = false)
    private String tunableItemPlataform;

    @Column(name = "tunable_item_title", nullable = false)
    private String tunableItemTitle;

    @Column(name = "tunable_item_artist", nullable = false)
    private String tunableItemArtist;

    @Column(name = "tunable_item_type", nullable = false)
    private String tunableItemType; // Guardamos como String no banco

    @Column(name = "tunable_item_artwork_url")
    private String tunableItemArtworkUrl;    


    // RELACIONAMENTOS FILHOS
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    // Métodos Úteis 

    public void validateTunableItem() {
        if (this.tunableItemId == null || this.tunableItemId.isBlank())
            throw new IllegalArgumentException("ID do item é obrigatório");
        if (this.tunableItemType == null)
            throw new IllegalArgumentException("Tipo do item é obrigatório");
    }

    /**
     * Reconstrói o Value Object a partir das colunas planas.
     * Útil se o Service precisar passar o objeto para outro lugar.
     */
    public TunableItem getTunableItem() {
        return TunableItem.builder()
                .id(this.tunableItemId)
                .platformName(this.tunableItemPlataform)
                .title(this.tunableItemTitle)
                .artist(this.tunableItemArtist)
                .itemType(TunableItemType.fromString(this.tunableItemType))
                .artworkUrl(this.tunableItemArtworkUrl != null ? URI.create(this.tunableItemArtworkUrl) : null)
                .build();
    }

    public void updateTunableItem(TunableItem tunableItem) {
        this.setTunableItemId(tunableItem.getId());
        this.setTunableItemArtist(tunableItem.getArtist());
        this.setTunableItemArtworkUrl(tunableItem.getArtworkUrl().toString());
        this.setTunableItemPlataform(tunableItem.getPlatformName());
        this.setTunableItemTitle(tunableItem.getTitle());
        this.setTunableItemType(tunableItem.getItemType().toString());        
    }

    @Transient
    @JsonProperty("totalComments")
    public int getTotalCommentsCount() {
        return comments != null ? comments.size() : 0;
    }

    @Transient
    @JsonProperty("totalLikes")
    public int getTotalLikesCount() {
        return likes != null ? likes.size() : 0;
    }

    @Override
    public String getContent() {
        return String.format("%s by %s (%s)",
                this.tunableItemTitle,
                this.tunableItemArtist,
                this.tunableItemType
        );
    }
}
