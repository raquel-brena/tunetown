package com.imd.backend.app.gateway.filmplataformgateway;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;

import java.util.List;

public interface FilmPlatformGateway extends IPostItemGateway<MovieItem> {
  List<MovieItem> searchItem(String query, String itemType);

  MovieItem getItemById(String id, String itemType);
}
