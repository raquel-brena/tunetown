package com.imd.backend.infra.persistence.jpa.projections;

import java.time.LocalDateTime;

public interface UserWithProfileProjection {
  String getUserId();

  String getUserEmail();

  String getUsername();

  String getProfileId();

  String getBio();

  String getFavoriteSong();

  LocalDateTime getCreatedAt();

  String getFileName();
}