package com.imd.backend.app.gateway.filmplataformgateway.tmdb.factory;

import com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbgetitembyid.TmdbGetItemById;
import com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbgetitembyid.TmdbGetMovieById;
import com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbgetitembyid.TmdbGetSeriesById;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueobjects.movieitem.FilmItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TmdbGetItemByIdFactory {
  private final TmdbGetMovieById getMovieById;
  private final TmdbGetSeriesById getSeriesById;

  public TmdbGetItemById create(FilmItemType type) {
    if(type == FilmItemType.MOVIE) return getMovieById;
    if (type == FilmItemType.SERIES) return getSeriesById;

    throw new BusinessException("Tipo de item não suportado pelo TMDB Gateway");
  }
}
