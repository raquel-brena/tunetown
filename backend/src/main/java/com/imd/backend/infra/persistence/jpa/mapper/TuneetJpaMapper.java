package com.imd.backend.infra.persistence.jpa.mapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import com.imd.backend.api.dto.TuneetResumoDTO;
import org.springframework.stereotype.Component;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.domain.entities.TuneetResume;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

@Component
public class TuneetJpaMapper {
  public static TuneetEntity fromTuneetDomain(Tuneet tuneet) {
      return TuneetEntity.builder()
              .id(tuneet.getId().toString())
              .authorId(tuneet.getAuthorId())
              .author(tuneet.getAuthor())
              .contentText(tuneet.getTextContent())
              .tunableItemId(tuneet.getItemId())
              .tunableItemPlataform(tuneet.getItemPlataform())
              .tunableItemTitle(tuneet.getItemTitle())
              .tunableItemArtist(tuneet.getItemArtist())
              .tunableItemType(tuneet.getItemType().toString())
              .tunableItemArtworkUrl(tuneet.getItemArtworkUrl().toString())
              .build();
  }

  public static TuneetEntity fromTuneetResumeDomain(TuneetResume tuneetResume) {
      return TuneetEntity.builder()
              .id(tuneetResume.getId().toString())
              .contentText(tuneetResume.getTextContent())
              .tunableItemId(tuneetResume.getItemId())
              .tunableItemPlataform(tuneetResume.getItemPlataform())
              .tunableItemTitle(tuneetResume.getItemTitle())
              .tunableItemArtist(tuneetResume.getItemArtist())
              .tunableItemType(tuneetResume.getItemType().toString())
              .tunableItemArtworkUrl(tuneetResume.getItemArtworkUrl().toString())
              .build();
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

  public Tuneet tuneetFromResumoDTO(TuneetResumoDTO dto) {
      return Tuneet.rebuild(
              UUID.fromString(dto.id()),
              dto.authorName(),
              dto.authorId(),
              dto.contentText(),
              new TunableItem(
                      dto.tunableItemId(),
                      dto.tunableItemPlataform(),
                      dto.tunableItemTitle(),
                      dto.tunableItemArtist(),
                      URI.create(dto.tunableItemArtworkUrl()),
                      TunableItemType.fromString(dto.tunableItemType())
              ),
              dto.createdAt(),
              dto.totalComments(),
              dto.totalLikes()
      );
  }



    public Tuneet tuneetFromJpaEntity(TuneetEntity entity) {
    return Tuneet.rebuild(
      UUID.fromString(entity.getId()),
      entity.getAuthorId(),
      entity.getAuthorName(),
      entity.getContentText(),
      new TunableItem(
        entity.getTunableItemId(),
        entity.getTunableItemPlataform(),
        entity.getTunableItemTitle(),
        entity.getTunableItemArtist(),
        URI.create((entity.getTunableItemArtworkUrl())),
        TunableItemType.fromString(entity.getTunableItemType()
      )
    ),
            entity.getCreatedAt(),
            entity.getTotalComments(),
            entity.getTotalLikes());
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
