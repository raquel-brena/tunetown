package com.imd.backend.api.dto.tunescore;

public record TuneScoreResponse(
        String originUserId,
        String destinationUserId,
        float score,
        String message
){ }
