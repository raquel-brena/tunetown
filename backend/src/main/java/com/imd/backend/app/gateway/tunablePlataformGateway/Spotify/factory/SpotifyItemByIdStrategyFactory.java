package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.factory;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.SpotifyApiClient;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.mapper.TunableItemSpotifyMapper;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifyItemByIdStrategy;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifyShowById;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.strategy.SpotifyTrackById;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;
import com.imd.backend.domain.exception.BusinessException;

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
      return new SpotifyTrackById(spotifyApiClient, mapper);

    if(itemType.equals(TunableItemType.PODCAST))
      return new SpotifyShowById(spotifyApiClient, mapper);
    
    throw new BusinessException("Erro ao criar estratégia de busca do spotify. Tipo de item inválido");
  }
}
