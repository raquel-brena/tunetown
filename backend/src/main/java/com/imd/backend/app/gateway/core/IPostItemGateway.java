package com.imd.backend.app.gateway.core;

import com.imd.backend.domain.valueobjects.core.PostItem;

import java.util.List;

public interface IPostItemGateway<I extends PostItem> {
  I getItemById(String id, String type);
  List<I> searchItem(String query, String itemType); 
}
