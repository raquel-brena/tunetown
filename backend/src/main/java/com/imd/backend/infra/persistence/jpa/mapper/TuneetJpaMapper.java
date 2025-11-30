package com.imd.backend.infra.persistence.jpa.mapper;

import java.net.URI;
import java.util.UUID;

import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.TuneetResume;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.projections.TimelineItemProjection;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TuneetJpaMapper {
  private final UserRepository userJPA;


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

  public TimeLineItem fromTimelineProjection(TimelineItemProjection p) {
    if (p == null) return null;

    return new TimeLineItem(
        p.getTuneetId() != null ? UUID.fromString(p.getTuneetId()) : null,
        p.getTextContent(),
        p.getCreatedAt(),
        p.getTotalComments(),
        p.getTotalLikes(),
        p.getTunableItemTitle(),
        p.getTunableItemArtist(),
        p.getTunableItemArtworkUrl() != null ? URI.create(p.getTunableItemArtworkUrl()) : null,
        p.getTunableItemType() != null ? TunableItemType.fromString(p.getTunableItemType()) : null,
        p.getAuthorId() != null ? UUID.fromString(p.getAuthorId()) : null,
        p.getAuthorUsername(),
        p.getAuthorAvatarUrl() != null ? URI.create(p.getAuthorAvatarUrl()) : null
    );
  }  
}
