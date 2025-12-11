package com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem;

import java.util.List;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpotifySearchShow implements SpotifySearchItemStrategy{
  private final SpotifyApiClient apiClient;
  private final TunableItemSpotifyMapper tunableItemMapper;

  @Override
  public List<TunableItem> execute(String query) {
    final var resultSearch = this.apiClient.search(query, "show");
    
    return resultSearch.shows().getItems()
      .stream()
      .map(item -> tunableItemMapper.fromSpotifyShow(item))
      .toList();
  }
}
