package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.mapper;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbCrewDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbMovieDetailDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbMovieResultDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbSeriesDetailDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbSeriesResultDTO;
import com.imd.backend.domain.valueObjects.movieItem.FilmItemType;
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
        year,
        FilmItemType.MOVIE
    );
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
        year,
        FilmItemType.MOVIE
    );
  }

  public MovieItem fromTmdbSeriesResult(TmdbSeriesResultDTO dto) {
    String fullArtworkUrl = dto.posterPath() != null ? IMAGE_BASE_URL + dto.posterPath() : null;
    String year = (dto.firstAirDate() != null && dto.firstAirDate().length() >= 4) 
                  ? dto.firstAirDate().substring(0, 4) : "N/A";

    return new MovieItem(
        String.valueOf(dto.id()),
        PLATFORM_NAME, // Ou apenas TMDB, se preferir
        dto.name(),
        fullArtworkUrl,
        "Showrunner", // Busca simples não traz criador
        year,
        FilmItemType.SERIES
    );
  }  

  public MovieItem fromTmdbSeriesDetail(TmdbSeriesDetailDTO dto) {
    String fullArtworkUrl = dto.posterPath() != null ? IMAGE_BASE_URL + dto.posterPath() : null;
    String year = (dto.firstAirDate() != null && dto.firstAirDate().length() >= 4) 
                  ? dto.firstAirDate().substring(0, 4) : "N/A";

    // Pega o primeiro criador da lista, se houver
    String creator = "Desconhecido";
    if (dto.createdBy() != null && !dto.createdBy().isEmpty()) {
        creator = dto.createdBy().get(0).name();
    }

    return new MovieItem(
        String.valueOf(dto.id()),
        "TMDB_SERIES",
        dto.name(),
        fullArtworkUrl,
        creator, // Mapeado para o campo 'director' do MovieItem
        year,
        FilmItemType.SERIES
    );
  }  
}
