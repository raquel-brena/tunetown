package com.imd.backend.app.gateway.tunableplataformgateway.spotify.factory;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem.SpotifySearchAlbum;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem.SpotifySearchItemStrategy;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem.SpotifySearchShow;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifysearchitem.SpotifySearchTrack;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
