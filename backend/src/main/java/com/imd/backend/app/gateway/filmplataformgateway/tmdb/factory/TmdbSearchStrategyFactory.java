package com.imd.backend.app.gateway.filmplataformgateway.tmdb.factory;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbsearchitem.TmdbSearchMovieStrategy;
import com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbsearchitem.TmdbSearchSeriesStrategy;
import com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbsearchitem.TmdbSearchStrategy;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueobjects.movieitem.FilmItemType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TmdbSearchStrategyFactory {
  private final TmdbSearchMovieStrategy movieStrategy;
  private final TmdbSearchSeriesStrategy seriesStrategy;
  
  public TmdbSearchStrategy create(FilmItemType type) {
    if (type == FilmItemType.MOVIE) return movieStrategy;
    if (type == FilmItemType.SERIES) return seriesStrategy;

    throw new BusinessException("Tipo de item não suportado pelo TMDB Gateway");
  }
}
