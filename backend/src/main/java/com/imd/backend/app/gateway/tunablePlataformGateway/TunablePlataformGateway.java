package com.imd.backend.app.gateway.tunablePlataformGateway;

import java.util.List;

import com.imd.backend.domain.entities.core.PostItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

public interface TunablePlataformGateway {
  public List<TunableItem> searchItem(String query, TunableItemType itemType);
  public PostItem getItemById(String id, TunableItemType itemType);
}
