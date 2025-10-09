package com.imd.backend.api.dto.tunescore;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TuneScoreStructuredResponse (
        @JsonProperty(required = true, value = "score") float score,
        @JsonProperty(required = true, value = "message") String message
){ }
