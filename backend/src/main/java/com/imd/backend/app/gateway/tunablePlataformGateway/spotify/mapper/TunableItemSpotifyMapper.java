package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.mapper;

import java.net.URI;

import com.imd.backend.domain.entities.core.PostItem;
import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.AlbumResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.ShowResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.TrackResponseDTO;
import com.imd.backend.domain.exception.TunableItemConvertionException;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

@Component
public class TunableItemSpotifyMapper {
  public static final String PLATAFORM_ID = "spotify";

  public PostItem fromSpotifyAlbum(AlbumResponseDTO albumDto) {
    try {
      TunableItem tuneet = new TunableItem(
          albumDto.id(),
          PLATAFORM_ID,
          albumDto.name(),
          albumDto.artists().getFirst().name(),
          new URI(albumDto.images().getFirst().url()),
          TunableItemType.ALBUM);

        return PostItem.builder().type(tuneet.getItemType().getTypeName())
                .title(tuneet.getTitle())
                .author(tuneet.getArtist())
                .plataform(tuneet.getPlataformId()).build();
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
          TunableItemType.MUSIC);
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
        TunableItemType.PODCAST
      );
    } catch (Exception e) {
      throw new TunableItemConvertionException(
        "Erro ao converter podcast do spotify em item tunetável: " + e.getLocalizedMessage(),
        e
      );
    }
  }  
}
