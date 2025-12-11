package com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifyitembyid;

import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;

public interface SpotifyItemByIdStrategy {
  public TunableItem execute(String id);
}
