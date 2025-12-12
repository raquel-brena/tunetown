package com.imd.backend.app.gateway.tunableplataformgateway.spotify.factory;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifyitembyid.SpotifyAlbumById;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifyitembyid.SpotifyItemByIdStrategy;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifyitembyid.SpotifyShowById;
import com.imd.backend.app.gateway.tunableplataformgateway.spotify.strategy.spotifyitembyid.SpotifyTrackById;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotifyItemByIdStrategyFactory {
  private final SpotifyApiClient spotifyApiClient;
  private final TunableItemSpotifyMapper mapper;

  public SpotifyItemByIdStrategy create(TunableItemType itemType) {
    if(itemType.equals(TunableItemType.MUSIC))
      return new SpotifyTrackById(spotifyApiClient, mapper);
    
    if(itemType.equals(TunableItemType.ALBUM))
      return new SpotifyAlbumById(spotifyApiClient, mapper);

    if(itemType.equals(TunableItemType.PODCAST))
      return new SpotifyShowById(spotifyApiClient, mapper);
    
    throw new BusinessException("Erro ao criar estratégia de busca do spotify. Tipo de item inválido");
  }
}
