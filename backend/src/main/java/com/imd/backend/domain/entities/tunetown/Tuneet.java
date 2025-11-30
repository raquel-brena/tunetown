package com.imd.backend.domain.entities.tunetown;

import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.PostItem;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @OneToMany(mappedBy = "tuneet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "tuneet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

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

    // --- FACTORY METHOD ---

    /**
     * Cria um novo Tuneet válido.
     * Recebe o VO TunableItem e "explode" ele nos campos da entidade.
     */
    public static Tuneet create(User author, String textContent, PostItem item) {
        Tuneet tuneet = Tuneet.builder()
                .id(UUID.randomUUID().toString())
                .author(author)
                .postItem(item)
                .textContent(textContent).build();

        // Validação herdada do BasePost (checa ID, autor e texto)
        tuneet.validateState();

        // Validações específicas do Tuneet
        tuneet.getPostItem().validateTunableItem();

        return tuneet;
    }
}
