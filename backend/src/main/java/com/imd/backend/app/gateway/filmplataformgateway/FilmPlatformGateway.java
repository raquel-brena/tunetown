package com.imd.backend.app.gateway.filmplataformgateway;

import java.util.List;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;

public interface FilmPlatformGateway extends IPostItemGateway<MovieItem> {
  List<MovieItem> searchItem(String query, String itemType);

  MovieItem getItemById(String id, String itemType);
}
