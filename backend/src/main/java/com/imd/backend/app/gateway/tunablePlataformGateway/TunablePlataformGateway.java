package com.imd.backend.app.gateway.tunablePlataformGateway;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

import java.util.List;


public interface TunablePlataformGateway {
  public List<TunableItem> searchItem(String query, TunableItemType itemType);
  public TunableItem getItemById(String id, TunableItemType itemType);
}
