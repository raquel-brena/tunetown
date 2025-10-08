package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifyItemById;

import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.TrackResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpotifyTrackById implements SpotifyItemByIdStrategy{
  private final SpotifyApiClient spotifyApiClient;
  private final TunableItemSpotifyMapper tunableItemMapper;

  @Override
  public TunableItem execute(String id) {
    final TrackResponseDTO track = this.spotifyApiClient.getTrackById(id);

    return tunableItemMapper.fromSpotifyTrack(track);
  }
  
}
