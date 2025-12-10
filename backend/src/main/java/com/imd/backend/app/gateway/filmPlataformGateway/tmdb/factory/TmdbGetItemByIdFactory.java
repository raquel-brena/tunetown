package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.factory;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.strategy.tmdbGetItemById.TmdbGetItemById;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueObjects.movieItem.FilmItemType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TmdbGetItemByIdFactory {
  private final TmdbGetItemById getMovieById;

  public TmdbGetItemById create(FilmItemType type) {
    if(type == FilmItemType.MOVIE)
      return getMovieById;

    throw new BusinessException("Tipo de item não suportado pelo TMDB Gateway");
  }
}
