package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.strategy.tmdbGetItemById;

import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

public interface TmdbGetItemById {
  MovieItem execute(String id);
}
