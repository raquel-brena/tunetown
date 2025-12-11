package com.imd.backend.domain.valueobjects;

import java.net.URI;

import com.imd.backend.domain.valueobjects.tunableitem.TunableItemType;


public record TrendingTuneResult(
    String itemId,
    String title,
    String artist,
    String platformId,
    TunableItemType itemType,
    URI artworkUrl,
    Long tuneetCount) {
}
