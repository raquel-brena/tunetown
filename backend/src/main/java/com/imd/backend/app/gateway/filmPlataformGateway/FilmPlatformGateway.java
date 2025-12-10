package com.imd.backend.app.gateway.filmPlataformGateway;

import java.util.List;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

public interface FilmPlatformGateway extends IPostItemGateway<MovieItem> {
  // A assinatura já vem do pai, mas reforçamos o contrato
  List<MovieItem> searchItem(String query, String itemType);

  MovieItem getItemById(String id, String itemType);
}
