package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.strategy.TmdbSearchItem;

import java.util.List;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.strategy.tmdbSearchItem.TmdbSearchStrategy;
import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.TmdbApiClient;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.mapper.MovieItemTmdbMapper;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TmdbSearchMovieStrategy implements TmdbSearchStrategy {
  private final TmdbApiClient apiClient;
  private final MovieItemTmdbMapper mapper;

  @Override
  public List<MovieItem> execute(String query) {
    var response = apiClient.searchMovies(query, "pt-BR");
    return response.results().stream()
        .map(mapper::fromTmdbResult)
        .toList();
  }
}
