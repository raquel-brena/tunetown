package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifyItemById;

import com.imd.backend.domain.entities.core.PostItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;

public interface SpotifyItemByIdStrategy {
  public PostItem execute(String id);
}
