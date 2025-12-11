package com.imd.backend.infra.persistence.jpa.projections;

import java.time.LocalDateTime;

public interface TimelineItemProjection {
  String getTuneetId();

  String getTextContent();

  LocalDateTime getCreatedAt();

  long getTotalComments();

  long getTotalLikes();

  String getTunableItemTitle();

  String getTunableItemArtist();

  String getTunableItemArtworkUrl();

  String getTunableItemType();

  String getAuthorId();

  String getAuthorUsername();

  String getAuthorAvatarUrl();

  String getAuthorAvatarFileName();
}
