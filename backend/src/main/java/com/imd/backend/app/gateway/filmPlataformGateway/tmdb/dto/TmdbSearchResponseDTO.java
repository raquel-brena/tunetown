package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto;

public record TmdbSearchResponseDTO(
    List<TmdbMovieResultDTO> results) {
}
