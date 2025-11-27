package com.imd.backend.domain.valueObjects.TunableItem;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public final class TunableItem {
  private final String id;
  private final String plataformId;
  private final String title;
  private final String artist;
  private final URI artworkUrl;
  private final TunableItemType itemType; 

  public TunableItem(
    String id,
    String plataformId,
    String title,
    String artist,
    URI artworkUrl,
    TunableItemType itemType
  ) {
    this.id = id;
    this.plataformId = plataformId;
    this.title = title;
    this.artist = artist;
    this.artworkUrl = artworkUrl;
    this.itemType = itemType;
  }
}
