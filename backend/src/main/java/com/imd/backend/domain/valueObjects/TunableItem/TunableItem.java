package com.imd.backend.domain.valueObjects.TunableItem;

import lombok.Builder;

import java.net.URI;

@Builder
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

  // Getters
  public String getPlataformId() {
    return this.plataformId;
  }

  public String getItemId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getArtist() {
    return this.artist;
  }

  public URI getArtworkUrl() {
    return this.artworkUrl;
  }

  public TunableItemType getItemType() {
    return this.itemType;
  }
}
