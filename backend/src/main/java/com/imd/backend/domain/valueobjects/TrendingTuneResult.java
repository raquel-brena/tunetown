package com.imd.backend.domain.valueobjects;

import com.imd.backend.domain.valueobjects.tunableitem.TunableItemType;

import java.net.URI;


public record TrendingTuneResult(
    String itemId,
    String title,
    String artist,
    String platformId,
    TunableItemType itemType,
    URI artworkUrl,
    Long tuneetCount) {
}
