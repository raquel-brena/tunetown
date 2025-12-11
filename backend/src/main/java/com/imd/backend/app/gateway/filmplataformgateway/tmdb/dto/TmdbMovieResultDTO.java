package com.imd.backend.app.gateway.filmplataformgateway.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Item da Busca
public record TmdbMovieResultDTO(
    String id,
    String title,
    @JsonProperty("release_date") String releaseDate,
    @JsonProperty("poster_path") String posterPath,
    String overview) {
}
