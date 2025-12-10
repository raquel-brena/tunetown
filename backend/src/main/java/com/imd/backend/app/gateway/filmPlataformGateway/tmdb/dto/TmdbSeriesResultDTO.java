package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbSeriesResultDTO(
    Long id,
    String name, // Diferente de filme (title)
    @JsonProperty("first_air_date") String firstAirDate, // Diferente de filme (release_date)
    @JsonProperty("poster_path") String posterPath,
    String overview) {
}
