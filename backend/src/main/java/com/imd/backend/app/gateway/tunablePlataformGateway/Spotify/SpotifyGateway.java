package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.entities.TunableItem.TunableItem;

public class SpotifyGateway implements TunablePlataformGateway {
  // TODO: Spotify

  @Override
  public TunableItem searchItem(String query) {
    throw new UnsupportedOperationException("Unimplemented method 'searchItem'");
  }

  @Override
  public TunableItem getItemById(String id) {
    throw new UnsupportedOperationException("Unimplemented method 'getItemById'");
  }
}
