package com.imd.backend.app.gateway.tunablePlataformGateway;

import com.imd.backend.domain.entities.TunableItem.TunableItem;

public interface TunablePlataformGateway {
    public TunableItem searchItem(String query);

    public TunableItem getItemById(String id);
}
