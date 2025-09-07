package com.imd.backend.core.gateway.TunablePlataformGateway;

import com.imd.backend.core.domain.TunableItem.TunableItem;

public interface TunablePlataformGateway {
  public TunableItem searchItem(String query);
  public TunableItem getItemById(String id);
}
