package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.factory;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifySearchItem.SpotifySearchAlbum;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifySearchItem.SpotifySearchItemStrategy;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifySearchItem.SpotifySearchShow;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifySearchItem.SpotifySearchTrack;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;
import com.imd.backend.domain.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpotifySearchItemStrategyFactory {
  private final SpotifyApiClient apiClient;
  private final TunableItemSpotifyMapper tunableItemMapper;

  public SpotifySearchItemStrategy create(TunableItemType itemType) {
    if(itemType.equals(TunableItemType.MUSIC))
      return new SpotifySearchTrack(apiClient, tunableItemMapper);
    
    if(itemType.equals(TunableItemType.ALBUM))
      return new SpotifySearchAlbum(apiClient, tunableItemMapper);

    if(itemType.equals(TunableItemType.PODCAST))
      return new SpotifySearchShow(apiClient, tunableItemMapper);
    
    throw new BusinessException("Erro ao criar estratégia de busca do spotify. Tipo de item inválido");    
  }
}
