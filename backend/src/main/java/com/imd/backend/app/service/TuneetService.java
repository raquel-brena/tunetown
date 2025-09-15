package com.imd.backend.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;
import com.imd.backend.domain.repository.TuneetRepository;

import jakarta.transaction.Transactional;

@Service
public class TuneetService {
  private final TunablePlataformGateway plataformGateway;
  private final TuneetRepository tuneetRepository;

  public TuneetService(
    @Qualifier("SpotifyGateway") TunablePlataformGateway plataformGateway,
    @Qualifier("TuneetJpaRepository") TuneetRepository tuneetRepository
  ) {
    this.plataformGateway = plataformGateway;
    this.tuneetRepository = tuneetRepository;
  }

  public List<TunableItem> searchTunableItems(String query, TunableItemType itemType) {
    return plataformGateway.searchItem(query, itemType);
  } 

  @Transactional(rollbackOn = Exception.class)
  public Tuneet createTuneet(
    String tunableItemId,
    TunableItemType tunableItemType,
    String textContent    
  ) {
    final TunableItem tunableItem = this.plataformGateway.getItemById(tunableItemId, tunableItemType);
    final Tuneet tuneetToSave = new Tuneet(textContent, tunableItem);
    
    this.tuneetRepository.save(tuneetToSave);
    return tuneetToSave;
  }
}
