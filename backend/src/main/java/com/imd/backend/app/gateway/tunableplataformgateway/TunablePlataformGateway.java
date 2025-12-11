package com.imd.backend.app.gateway.tunableplataformgateway;

import java.util.List;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;

public interface TunablePlataformGateway extends IPostItemGateway<TunableItem>{
  public List<TunableItem> searchItem(String query, String itemType);
  public TunableItem getItemById(String id, String itemType);
}
