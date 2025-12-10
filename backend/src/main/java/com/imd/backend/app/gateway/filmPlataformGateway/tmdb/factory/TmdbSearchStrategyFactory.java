package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.factory;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.strategy.tmdbSearchItem.TmdbSearchStrategy;
import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.strategy.TmdbSearchItem.TmdbSearchMovieStrategy;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueObjects.movieItem.FilmItemType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TmdbSearchStrategyFactory {
  private final TmdbSearchMovieStrategy movieStrategy;

  public TmdbSearchStrategy create(FilmItemType type) {
    if (type == FilmItemType.MOVIE) {
      return movieStrategy;
    }
    // Se tivesse SERIES, retornaria seriesStrategy
    throw new BusinessException("Tipo de item não suportado pelo TMDB Gateway");
  }
}
