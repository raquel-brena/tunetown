package com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem;

import java.util.List;

import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;

public interface SpotifySearchItemStrategy {
  public List<TunableItem> execute(String query);
}
