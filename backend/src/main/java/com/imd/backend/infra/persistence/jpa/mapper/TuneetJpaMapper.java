package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.domain.valueobjects.TimeLineItem;
import com.imd.backend.domain.valueobjects.TuneetResume;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.projections.TimelineItemProjection;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.UUID;

@Component
public class TuneetJpaMapper {

  public static TuneetResume resumeFromProjection(TuneetResumeProjection projection) {
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

  public static TimeLineItem fromTimelineProjection(TimelineItemProjection p) {
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
