package com.imd.backend.app.gateway.core;

import java.util.List;

import com.imd.backend.domain.valueobjects.core.PostItem;

public interface IPostItemGateway<I extends PostItem> {
  I getItemById(String id, String type);
  List<I> searchItem(String query, String itemType); 
}
