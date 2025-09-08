package com.imd.backend.domain.entities;

import java.util.UUID;

import com.imd.backend.domain.entities.TunableItem.TunableItem;

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

    this.tunabbleItem = tunabbleItem;
  }

  // Getters
  public UUID getId() {
    return this.id;
  }

  public String getTextContent() {
    return this.textContent;
  }

  public TunableItem getTunableItem() {
    return this.tunabbleItem;
  }
}
