package com.imd.backend.api.dto.tunescore;

public record TuneScoreResponse(
        String originUsername,
        String destinationUsername,
        float score,
        String message
){ }
