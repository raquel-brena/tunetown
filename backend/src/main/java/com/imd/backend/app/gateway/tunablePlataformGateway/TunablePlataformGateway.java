package com.imd.backend.app.gateway.tunablePlataformGateway;

import java.util.List;

import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;

public interface TunablePlataformGateway {
  public List<TunableItem> searchItem(String query, TunableItemType itemType);
  public TunableItem getItemById(String id, TunableItemType itemType);
}
