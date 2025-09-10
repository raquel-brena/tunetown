package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifyItemById;

import com.imd.backend.domain.entities.TunableItem.TunableItem;

public interface SpotifyItemByIdStrategy {
  public TunableItem execute(String id);   
}
