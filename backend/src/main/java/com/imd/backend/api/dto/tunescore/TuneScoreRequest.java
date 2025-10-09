package com.imd.backend.api.dto.tunescore;

public record TuneScoreRequest(
        String originUserId,
        String destinationUserId
) { }
