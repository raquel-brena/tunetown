package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.factory;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifyItemById.SpotifyAlbumById;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifyItemById.SpotifyItemByIdStrategy;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifyItemById.SpotifyShowById;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifyItemById.SpotifyTrackById;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

import lombok.RequiredArgsConstructor;

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
