package com.imd.backend.infra.persistence.jpa.mapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TuneetResume;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

@Component
public class TuneetJpaMapper {
  public TuneetEntity fromTuneetDomain(Tuneet tuneet) {
    return new TuneetEntity(
      tuneet.getId().toString(),
      tuneet.getTextContent(),
      tuneet.getItemId(),
      tuneet.getItemPlataform(),
      tuneet.getItemTitle(),
      tuneet.getItemArtist(),
      tuneet.getItemType().toString(),
      tuneet.getItemArtworkUrl().toString()
    );
  }

  public TuneetResume resumeFromTuneetJpaEntity(TuneetEntity entity) throws URISyntaxException {
    return new TuneetResume(
      UUID.fromString(entity.getId()),
      entity.getContentText(),
      new TunableItem(
        entity.getTunableItemId(),
        entity.getTunableItemPlataform(),
        entity.getTunableItemTitle(),
        entity.getTunableItemArtist(),
        new URI(entity.getTunableItemArtworkUrl()),
        TunableItemType.fromString(entity.getTunableItemType())
      )
    );
  }
}
