package com.imd.backend.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;

@Service
public class TuneetService {
  private final TunablePlataformGateway plataformGateway;

  public TuneetService(
    @Qualifier("SpotifyGateway") TunablePlataformGateway plataformGateway
  ) {
    this.plataformGateway = plataformGateway;
  }

  public List<TunableItem> searchTunableItems(String query, TunableItemType itemType) {
    return plataformGateway.searchItem(query, itemType);
  } 
}
