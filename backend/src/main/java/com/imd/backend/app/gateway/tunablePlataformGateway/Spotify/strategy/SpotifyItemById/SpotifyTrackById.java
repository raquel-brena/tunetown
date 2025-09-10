package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifyItemById;

import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.TrackResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.domain.entities.TunableItem.TunableItem;

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
