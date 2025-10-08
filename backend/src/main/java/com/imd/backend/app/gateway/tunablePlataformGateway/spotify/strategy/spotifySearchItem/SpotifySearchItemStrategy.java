package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifySearchItem;

import java.util.List;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;

public interface SpotifySearchItemStrategy {
  public List<TunableItem> execute(String query);
}
