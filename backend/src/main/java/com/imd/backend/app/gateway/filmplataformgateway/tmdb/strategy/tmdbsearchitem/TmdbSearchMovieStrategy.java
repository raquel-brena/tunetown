package com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbsearchitem;

import java.util.List;

import com.imd.backend.app.gateway.filmplataformgateway.tmdb.TmdbApiClient;
import com.imd.backend.app.gateway.filmplataformgateway.tmdb.mapper.MovieItemTmdbMapper;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;

import org.springframework.stereotype.Component;

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
