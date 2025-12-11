package com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbgetitembyid;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmplataformgateway.tmdb.TmdbApiClient;
import com.imd.backend.app.gateway.filmplataformgateway.tmdb.mapper.MovieItemTmdbMapper;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TmdbGetMovieById implements TmdbGetItemById{
  private final TmdbApiClient apiClient;
  private final MovieItemTmdbMapper mapper;

  @Override
  public MovieItem execute(String id) {
    var response = apiClient.getMovieById(id, "pt-BR", "credits");
    return mapper.fromTmdbDetail(response);
  }
}
