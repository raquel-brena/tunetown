package com.imd.backend.infra.persistence.jpa.mapper;

import org.springframework.stereotype.Component;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

@Component
public class TuneetJpaMapper {
  public TuneetEntity fromTuneetDomain(Tuneet tuneet) {
    return new TuneetEntity(
      tuneet.getId().toString(),
      tuneet.getTextContent(),
      tuneet.getItemId(),
      tuneet.getItemPlataform(),
      tuneet.getItemArtist(),
      tuneet.getItemType().toString(),
      tuneet.getItemArtworkUrl().toString()
    );
  }
}
