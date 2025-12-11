package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbSeriesDetailDTO(
    Long id,
    String name,
    @JsonProperty("first_air_date") String firstAirDate,
    @JsonProperty("poster_path") String posterPath,
    @JsonProperty("created_by") List<TmdbCreatorDTO> createdBy // Séries têm criadores
) {
}
