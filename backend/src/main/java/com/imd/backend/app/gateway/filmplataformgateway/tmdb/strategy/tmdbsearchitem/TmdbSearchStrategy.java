package com.imd.backend.app.gateway.filmplataformgateway.tmdb.strategy.tmdbsearchitem;

import com.imd.backend.domain.valueobjects.movieitem.MovieItem;

import java.util.List;

public interface TmdbSearchStrategy {
  List<MovieItem> execute(String query);
}