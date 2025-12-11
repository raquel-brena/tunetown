package com.imd.backend.api.dto.similarity;

public record SimilarityResponse(
        String originUserId,
        String destinationUserId,
        float score,
        String message
) { }
