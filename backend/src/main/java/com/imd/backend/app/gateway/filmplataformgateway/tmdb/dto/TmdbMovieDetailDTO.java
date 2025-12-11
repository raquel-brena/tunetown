package com.imd.backend.app.gateway.filmplataformgateway.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Detalhe Completo (inclui Créditos para achar o diretor)
public record TmdbMovieDetailDTO(
    String id,
    String title,
    @JsonProperty("release_date") String releaseDate,
    @JsonProperty("poster_path") String posterPath,
    TmdbCreditsDTO credits) {
}
