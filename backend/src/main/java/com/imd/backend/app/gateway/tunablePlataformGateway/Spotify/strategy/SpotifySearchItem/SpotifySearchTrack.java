package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifySearchItem;

import java.util.List;

import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.domain.entities.TunableItem.TunableItem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpotifySearchTrack implements SpotifySearchItemStrategy {
  private final SpotifyApiClient apiClient;
  private final TunableItemSpotifyMapper tunableItemMapper;

  @Override
  public List<TunableItem> execute(String query) {
    final var resultSearch = this.apiClient.search(query, "track");
    
    return resultSearch.tracks().getItems()
      .stream()
      .map(item -> tunableItemMapper.fromSpotifyTrack(item))
      .toList();
  }
}
