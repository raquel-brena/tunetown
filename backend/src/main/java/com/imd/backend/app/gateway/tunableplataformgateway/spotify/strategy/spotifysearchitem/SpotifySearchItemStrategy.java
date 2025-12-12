package com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem;

import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;

import java.util.List;

public interface SpotifySearchItemStrategy {
  public List<TunableItem> execute(String query);
}
