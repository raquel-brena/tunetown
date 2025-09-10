package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifyItemById;

import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.ShowResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.domain.entities.TunableItem.TunableItem;

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
