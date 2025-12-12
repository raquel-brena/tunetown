package com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SpotifySearchAlbum implements SpotifySearchItemStrategy {
  private final SpotifyApiClient apiClient;
  private final TunableItemSpotifyMapper tunableItemMapper;

  @Override
  public List<TunableItem> execute(String query) {
    final var resultSearch = this.apiClient.search(query, "album");
    
    return resultSearch.albums().getItems()
      .stream()
      .map(item -> tunableItemMapper.fromSpotifyAlbum(item))
      .toList();
  }
}
