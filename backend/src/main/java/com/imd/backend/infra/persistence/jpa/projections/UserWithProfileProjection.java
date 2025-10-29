package com.imd.backend.infra.persistence.jpa.projections;

import java.util.Date;

public interface UserWithProfileProjection {
  String getUserId();

  String getUserEmail();

  String getUsername();

  String getProfileId();

  String getBio();

  String getFavoriteSong();

  Date getCreatedAt();
}