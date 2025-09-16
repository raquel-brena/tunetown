package com.imd.backend.domain.entities;

import java.net.URI;
import java.util.UUID;

import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TuneetResume {
  private UUID id;
  private String textContent;
  private TunableItem tunableItem;

  // Getters
  public UUID getId() {
    return this.id;
  }

  public String getTextContent() {
    return this.textContent;
  }

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
}
