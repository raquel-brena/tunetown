package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.strategy.tmdbSearchItem;

import java.util.List;

import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

public interface TmdbSearchStrategy {
  List<MovieItem> execute(String query);
}