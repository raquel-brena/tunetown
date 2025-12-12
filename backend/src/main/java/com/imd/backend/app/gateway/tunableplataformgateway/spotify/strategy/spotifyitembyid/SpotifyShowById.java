package com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifyitembyid;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto.ShowResponseDTO;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpotifyShowById implements SpotifyItemByIdStrategy {
  private final SpotifyApiClient spotifyApiClient;
  private final TunableItemSpotifyMapper tunableItemMapper;

  @Override
  public TunableItem execute(String id) {
    final ShowResponseDTO show = this.spotifyApiClient.getShowById(id);

    return tunableItemMapper.fromSpotifyShow(show);
  }
}
