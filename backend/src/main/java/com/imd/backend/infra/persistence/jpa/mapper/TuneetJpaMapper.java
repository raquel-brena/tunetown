package com.imd.backend.infra.persistence.jpa.mapper;

import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

@Component
public class TuneetJpaMapper {
  public TuneetEntity fromTuneetDomain(Tuneet tuneet) {
    if(tuneet == null) return null;
    
    return new TuneetEntity(
      tuneet.getId().toString(),
      tuneet.getAuthorId().toString(),
      tuneet.getTextContent(),
      tuneet.getItemId(),
      tuneet.getItemPlataform(),
      tuneet.getItemTitle(),
      tuneet.getItemArtist(),
      tuneet.getItemType().toString(),
      tuneet.getItemArtworkUrl().toString()
    );
  }

  public Tuneet tuneetFromJpaEntity(TuneetEntity entity) {
    return Tuneet.rebuild(
      UUID.fromString(entity.getId()),
      UUID.fromString(entity.getAuthorId()),
      entity.getContentText(),
      new TunableItem(
        entity.getTunableItemId(),
        entity.getTunableItemPlataform(), 
        entity.getTunableItemTitle(), 
        entity.getTunableItemArtist(), 
        URI.create((entity.getTunableItemArtworkUrl())),
        TunableItemType.fromString(entity.getTunableItemType()))  
    );
  }

  // public TuneetEntity fromTuneetResumeDomain(TuneetResume tuneetResume) {
  //   if(tuneetResume == null) return null;
    
  //   return new TuneetEntity(
  //     tuneetResume.getId().toString(),
  //     tuneetResume.getTextContent(),
  //     tuneetResume.getItemId(),
  //     tuneetResume.getItemPlataform(),
  //     tuneetResume.getItemTitle(),
  //     tuneetResume.getItemArtist(),
  //     tuneetResume.getItemType().toString(),
  //     tuneetResume.getItemArtworkUrl().toString()
  //   );    
  // }

  // public TuneetResume resumeFromTuneetJpaEntity(TuneetEntity entity) throws URISyntaxException {
  //   if(entity == null) return null;
    
  //   return new TuneetResume(
  //     UUID.fromString(entity.getId()),
  //     entity.getContentText(),
  //     new TunableItem(
  //       entity.getTunableItemId(),
  //       entity.getTunableItemPlataform(),
  //       entity.getTunableItemTitle(),
  //       entity.getTunableItemArtist(),
  //       new URI(entity.getTunableItemArtworkUrl()),
  //       TunableItemType.fromString(entity.getTunableItemType())
  //     )
  //   );
  // }
}
