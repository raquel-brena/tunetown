package com.imd.backend.core.domain.TunableItem;

import java.net.URI;

public interface TunableItem {
  public String getPlataformId();
  public String getTitle();
  public String getArtist();
  public URI getArtworkUrl();
  public TunableItemType getItemType();
}
