package com.imd.backend.domain.entities;

import java.net.URI;
import java.util.UUID;

import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;

/**
 * Representa um "post" (tuneet) de um usuário
 */
public class Tuneet {
  private final UUID id;
  private String textContent;
  // private User author;
  private TunableItem tunabbleItem;

  public Tuneet(String textContent, TunableItem tunabbleItem) {
    this.id = UUID.randomUUID();

    this.textContent = textContent;
    this.tunabbleItem = tunabbleItem;
  }

  // Getters
  public UUID getId() {
    return this.id;
  }

  public String getTextContent() {
    return this.textContent;
  }

  public String getItemId() {
    return this.tunabbleItem.getItemId();
  }

  public String getItemPlataform() {
    return this.tunabbleItem.getPlataformId();
  }

  public String getItemTitle() {
    return this.tunabbleItem.getTitle();
  }

  public String getItemArtist() {
    return this.tunabbleItem.getArtist();
  }

  public URI getItemArtworkUrl() {
    return this.tunabbleItem.getArtworkUrl();
  }

  public TunableItemType getItemType() {
    return this.tunabbleItem.getItemType();
  }
}
