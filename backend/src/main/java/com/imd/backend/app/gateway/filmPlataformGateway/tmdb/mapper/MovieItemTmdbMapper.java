package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.mapper;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbCrewDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbMovieDetailDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbMovieResultDTO;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

@Component
public class MovieItemTmdbMapper {

  private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
  private static final String PLATFORM_NAME = "TMDB";

  public MovieItem fromTmdbResult(TmdbMovieResultDTO dto) {
    String fullArtworkUrl = dto.posterPath() != null ? IMAGE_BASE_URL + dto.posterPath() : null;
    String year = (dto.releaseDate() != null && dto.releaseDate().length() >= 4)
        ? dto.releaseDate().substring(0, 4)
        : "N/A";

    return new MovieItem(
        String.valueOf(dto.id()), // TMDB usa Long, convertemos para String
        PLATFORM_NAME,
        dto.title(),
        fullArtworkUrl,
        "Desconhecido", // Busca simples não retorna diretor no TMDB, infelizmente
        year);
  }

  public MovieItem fromTmdbDetail(TmdbMovieDetailDTO dto) {
    String fullArtworkUrl = dto.posterPath() != null ? IMAGE_BASE_URL + dto.posterPath() : null;
    String year = (dto.releaseDate() != null && dto.releaseDate().length() >= 4)
        ? dto.releaseDate().substring(0, 4)
        : "N/A";

    // Lógica para achar o Diretor na lista de Crew
    String director = dto.credits().crew().stream()
        .filter(c -> "Director".equals(c.job()))
        .map(TmdbCrewDTO::name)
        .findFirst()
        .orElse("Desconhecido");

    return new MovieItem(
        String.valueOf(dto.id()),
        PLATFORM_NAME,
        dto.title(),
        fullArtworkUrl,
        director,
        year);
  }
}
