package com.imd.backend.app.gateway.tunableplataformgateway;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;

import java.util.List;

public interface TunablePlataformGateway extends IPostItemGateway<TunableItem>{
  public List<TunableItem> searchItem(String query, String itemType);
  public TunableItem getItemById(String id, String itemType);
}
