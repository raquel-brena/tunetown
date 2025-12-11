package com.imd.backend.app.gateway.filmPlataformGateway.tmdb;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.filmPlataformGateway.FilmPlatformGateway;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.factory.TmdbGetItemByIdFactory;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.factory.TmdbSearchStrategyFactory;
import com.imd.backend.domain.valueObjects.movieItem.FilmItemType;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

import lombok.RequiredArgsConstructor;

@Component
@Qualifier("TmdbGateway")
@RequiredArgsConstructor
public class TmdbGateway implements FilmPlatformGateway {

  private final TmdbSearchStrategyFactory searchFactory;
  private final TmdbGetItemByIdFactory byIdFactory;

  @Override
  public List<MovieItem> searchItem(String query, String itemType) {
    FilmItemType type = FilmItemType.fromString(itemType);
    return searchFactory.create(type).execute(query);
  }

  @Override
  public MovieItem getItemById(String id, String itemType) {
    FilmItemType type = FilmItemType.fromString(itemType);
    return byIdFactory.create(type).execute(id);
  }
}
