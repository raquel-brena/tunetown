package com.imd.backend.infra.persistence.jpa.projections;

import java.time.LocalDateTime;

public interface TuneetResumeProjection {
  String getTuneetId();
  String getContentText();
  String getTunableItemArtist();
  String getTunableItemTitle();
  String getTunableItemArtworkUrl();
  String getTunableItemId();
  String getTunableItemPlataform();
  String getTunableItemType();
  LocalDateTime getCreatedAt();
  String getUsername();
  String getProfileId();
  String getEmail();
  String getAuthorId();
  long getTotalComments();
  long getTotalLikes();
  String getBio();
  long getTotalFollowers();
  long getTotalFollowing();
  //String getUrlPhoto();
  String getFileNamePhoto();  
}
