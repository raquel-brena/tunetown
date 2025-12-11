package com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbsearchitem;

import java.util.List;

import com.imd.backend.domain.valueobjects.movieitem.MovieItem;

public interface TmdbSearchStrategy {
  List<MovieItem> execute(String query);
}