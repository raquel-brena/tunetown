package com.imd.backend.infra.persistence.jpa.projections;

public interface TrendingTuneProjection {
  String getItemId();

  String getTitle();

  String getArtist();

  String getPlatformId();

  String getItemType();

  String getArtworkUrl();

  Long getTuneetCount();
}
