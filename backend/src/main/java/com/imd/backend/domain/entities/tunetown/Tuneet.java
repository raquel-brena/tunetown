package com.imd.backend.domain.entities.tunetown;

import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
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
  private String tunableItemType; // Armazenamos como String no banco

  @Column(name = "tunable_item_artwork_url")
  private String tunableItemArtworkUrl;


  @OneToMany(mappedBy = "tuneet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default 
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "tuneet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default 
  private List<Like> likes = new ArrayList<>();

  // --- FACTORY METHOD ---

  /**
   * Cria um novo Tuneet válido.
   * Recebe o VO TunableItem e "explode" ele nos campos da entidade.
   */
  public static Tuneet create(User author, String textContent, TunableItem item) {
    Tuneet tuneet = Tuneet.builder()
        .id(UUID.randomUUID().toString())
        .author(author)
        .textContent(textContent)
        // Mapeamento do VO para os campos da entidade
        .tunableItemId(item.getId())
        .tunableItemPlataform(item.getPlataformId())
        .tunableItemTitle(item.getTitle())
        .tunableItemArtist(item.getArtist())
        .tunableItemType(item.getItemType().toString())
        .tunableItemArtworkUrl(item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null)
        .build();

    // Validação herdada do BasePost (checa ID, autor e texto)
    tuneet.validateState();

    // Validações específicas do Tuneet
    tuneet.validateTunableItem();

    return tuneet;
  }



    // --- MÉTODOS DE DOMÍNIO ---

  private void validateTunableItem() {
    if (this.tunableItemId == null || this.tunableItemId.isBlank())
      throw new IllegalArgumentException("O item tunetável deve ter um ID.");
    if (this.tunableItemTitle == null || this.tunableItemTitle.isBlank())
      throw new IllegalArgumentException("O item tunetável deve ter um título.");
  }

  /**
   * Reconstrói o Value Object se necessário para uso na aplicação.
   */
  public TunableItem getTunableItem() {
    return new TunableItem(
        this.tunableItemId,
        this.tunableItemPlataform,
        this.tunableItemTitle,
        this.tunableItemArtist,
        this.tunableItemArtworkUrl != null ? URI.create(this.tunableItemArtworkUrl) : null,
        TunableItemType.fromString(this.tunableItemType));
  }

  /**
   * Método de negócio para formatação de exibição.
   */
  public String getTunableContent() {
    return String.format("%s by %s (%s)",
        this.tunableItemTitle,
        this.tunableItemArtist,
        this.tunableItemType);
  }

  /**
   * Atualiza os dados do item tunetável (ex: se mudar a URL da capa).
   */
  public void updateTunableItem(TunableItem newItem) {
    if (newItem == null)
      throw new IllegalArgumentException("Item não pode ser nulo");

    this.tunableItemId = newItem.getId();
    this.tunableItemPlataform = newItem.getPlataformId();
    this.tunableItemTitle = newItem.getTitle();
    this.tunableItemArtist = newItem.getArtist();
    this.tunableItemType = newItem.getItemType().toString();
    this.tunableItemArtworkUrl = newItem.getArtworkUrl() != null ? newItem.getArtworkUrl().toString() : null;
  }
}
