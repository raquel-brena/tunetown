package com.imd.backend.domain.valueObjects;

import java.net.URI;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;


public record TrendingTuneResult(
    String itemId,
    String title,
    String artist,
    String platformId,
    TunableItemType itemType,
    URI artworkUrl,
    Long tuneetCount) {
}
