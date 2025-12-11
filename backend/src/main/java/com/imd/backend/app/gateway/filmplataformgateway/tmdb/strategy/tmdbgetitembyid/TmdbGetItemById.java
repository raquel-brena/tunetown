package com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbgetitembyid;

import com.imd.backend.domain.valueobjects.movieitem.MovieItem;

public interface TmdbGetItemById {
  MovieItem execute(String id);
}
