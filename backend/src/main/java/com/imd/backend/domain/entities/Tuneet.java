package com.imd.backend.domain.entities;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.imd.backend.domain.exception.InvalidEntityAttributesException;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa um "post" (tuneet) de um usuário
 */
@Getter
@Setter
public class Tuneet {
  private final UUID id;
  private String textContent;
  private String authorId;
  private TunableItem tunableItem;

  private Tuneet(UUID id, String authorId, String textContent, TunableItem tunableItem) {

    this.id = id;
    this.authorId = authorId;
    this.textContent = textContent;
    this.tunableItem = tunableItem;
    this.validateAttributes();
  }

  public static Tuneet createNew(String authorId, String textContent, TunableItem item) {
    final UUID id = UUID.randomUUID();
    
    return new Tuneet(id, authorId, textContent, item);
  }

  public static Tuneet rebuild(
    UUID id,
    String authorId,
    String textContent,
    TunableItem item
  ) {
    return new Tuneet(id, authorId, textContent, item);
  }

  // Getters
  public String getItemId() {
    return this.tunableItem.getItemId();
  }

  public String getItemPlataform() {
    return this.tunableItem.getPlataformId();
  }

  public String getItemTitle() {
    return this.tunableItem.getTitle();
  }

  public String getItemArtist() {
    return this.tunableItem.getArtist();
  }

  public URI getItemArtworkUrl() {
    return this.tunableItem.getArtworkUrl();
  }

  public TunableItemType getItemType() {
    return this.tunableItem.getItemType();
  }

  // Setters
  public void setTextContent(String textContent) {
    this.textContent = textContent;
    this.validateAttributes();
  }
  
  public void setTunableItem(TunableItem tunableItem) {
    this.tunableItem = tunableItem;
    this.validateAttributes();
  }

  // Private methods
  private void validateAttributes() {
    final Map<String, String> errors = new HashMap<>();
    
    if (id == null)
      errors.put("id", "O ID não pode ser nulo");

    if (textContent == null || textContent.isBlank())
      errors.put("textContent", "O conteúdo de texto do tuneet não pode estar vazio");

    if (tunableItem == null)
      errors.put("tunableItem", "O item tunetável não pode ser nulo");

    if (!errors.isEmpty())
      throw new InvalidEntityAttributesException("Atributos inválidos", errors);    
  }

    public String getTunableContent() {
      return String.format("%s by %s (%s)",
        tunableItem.getTitle(),
        tunableItem.getArtist(),
        tunableItem.getItemType()
      );
    }
}
