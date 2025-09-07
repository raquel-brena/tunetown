package com.imd.backend.app.gateway.tunablePlataformGateway;

import com.imd.backend.core.domain.TunableItem.TunableItem;
import com.imd.backend.core.gateway.TunablePlataformGateway.TunablePlataformGateway;

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
