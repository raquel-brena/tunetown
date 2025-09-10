package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifySearchItem;

import java.util.List;

import com.imd.backend.domain.entities.TunableItem.TunableItem;

public interface SpotifySearchItemStrategy {
  public List<TunableItem> execute(String query);
}
