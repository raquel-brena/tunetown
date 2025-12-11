package com.imd.backend.app.gateway.tunablePlataformGateway;

import java.util.List;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;

public interface TunablePlataformGateway extends IPostItemGateway<TunableItem>{
  public List<TunableItem> searchItem(String query, String itemType);
  public TunableItem getItemById(String id, String itemType);
}
