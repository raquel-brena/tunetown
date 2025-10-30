package com.imd.backend.infra.persistence.jpa.mapper;

import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.valueObjects.TuneetResume;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;
import com.imd.backend.infra.persistence.jpa.repository.user.UserJPA;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TuneetJpaMapper {
  private final UserJPA userJPA;

  public TuneetEntity fromTuneetDomain(Tuneet tuneet) {
    if(tuneet == null) return null;

    final TuneetEntity entity = new TuneetEntity();

    entity.setId(tuneet.getId().toString());
    entity.setContentText(tuneet.getTextContent());
    entity.setTunableItemId(tuneet.getItemId());
    entity.setTunableItemPlataform(tuneet.getItemPlataform());
    entity.setTunableItemTitle(tuneet.getItemTitle());
    entity.setTunableItemType(tuneet.getItemType().toString());
    entity.setTunableItemArtist(tuneet.getItemArtist());
    entity.setTunableItemArtworkUrl(tuneet.getItemArtworkUrl().toString());
    entity.setCreatedAt(tuneet.getCreatedAt()); 

    if(tuneet.getAuthorId() != null) {
      final UserEntity authorRef = userJPA.getReferenceById(tuneet.getAuthorId().toString());
      entity.setAuthor(authorRef);
    }

    return entity;
  }

  public Tuneet tuneetFromJpaEntity(TuneetEntity entity) {
    if(entity == null) return null;

    final UUID authorId = (entity.getAuthor() != null) ? UUID.fromString(entity.getAuthor().getId()) : null;

    return Tuneet.rebuild(
      UUID.fromString(entity.getId()),
      authorId,
      entity.getContentText(),
      new TunableItem(
        entity.getTunableItemId(), 
        entity.getTunableItemPlataform(), 
        entity.getTunableItemTitle(), 
        entity.getTunableItemArtist(),
        URI.create(entity.getTunableItemArtworkUrl()),
        TunableItemType.fromString(entity.getTunableItemType())
      ),
      entity.getCreatedAt()
    );    
  }

  public TuneetResume resumeFromProjection(TuneetResumeProjection projection) {
    if(projection == null) return null;

    // --- Defesa para UUIDs ---
    UUID tuneetId = (projection.getTuneetId() != null)
        ? UUID.fromString(projection.getTuneetId())
        : null;

    UUID authorId = (projection.getAuthorId() != null)
        ? UUID.fromString(projection.getAuthorId())
        : null;

    // --- Defesa para Enum ---
    TunableItemType itemType = (projection.getTunableItemType() != null)
        ? TunableItemType.fromString(projection.getTunableItemType())
        : null;

    String urlPhoto = null;

    return new TuneetResume(
        tuneetId, 
        projection.getContentText(),
        projection.getTunableItemArtist(),
        projection.getTunableItemTitle(),
        projection.getTunableItemArtworkUrl(),
        projection.getTunableItemId(),
        projection.getTunableItemPlataform(),
        itemType, 
        projection.getCreatedAt(),
        projection.getUsername(),
        projection.getProfileId(),
        projection.getEmail(),
        authorId, 
        projection.getTotalComments(),
        projection.getTotalLikes(),
        projection.getBio(),
        projection.getTotalFollowers(),
        projection.getTotalFollowing(),
        urlPhoto,
        projection.getFileNamePhoto()
    );
  }
}
