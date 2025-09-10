package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.mapper;

import java.net.URI;

import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.AlbumResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.ShowResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.TrackResponseDTO;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;
import com.imd.backend.domain.exception.TunableItemConvertionException;

public class TunableItemSpotifyMapper {
  public static final String PLATAFORM_ID = "spotify";

  public TunableItem fromSpotifyAlbum(AlbumResponseDTO albumDto) {
    try {
      return new TunableItem(
          albumDto.id(),
          PLATAFORM_ID,
          albumDto.name(),
          albumDto.artists().getFirst().name(),
          new URI(albumDto.images().getFirst().url()),
          TunableItemType.ALBUM);      
    } catch (Exception e) {
      throw new TunableItemConvertionException(
        "Erro ao converter album do spotify em item tunetável: " + e.getLocalizedMessage(), 
        e
      );
    }
  }

  public TunableItem fromSpotifyTrack(TrackResponseDTO trackDto) {
    try {
      return new TunableItem(
          trackDto.id(),
          PLATAFORM_ID,
          trackDto.name(),
          trackDto.artists().getFirst().name(),
          new URI(trackDto.album().images().getFirst().url()),
          TunableItemType.ALBUM);
    } catch (Exception e) {
      throw new TunableItemConvertionException(
        "Erro ao converter música do spotify em item tunetável: " + e.getLocalizedMessage(),
        e
      );
    }    
  }

  public TunableItem fromSpotifyShow(ShowResponseDTO showDto) {
    try {
      return new TunableItem(
          showDto.id(),
          PLATAFORM_ID,
          showDto.name(),
          showDto.publisher(),
          new URI(showDto.images().getFirst().url()),
          TunableItemType.ALBUM);
    } catch (Exception e) {
      throw new TunableItemConvertionException(
          "Erro ao converter podcast do spotify em item tunetável: " + e.getLocalizedMessage(),
          e);
    }
  }  
}
