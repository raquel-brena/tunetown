package com.imd.backend.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TuneetResume;
import com.imd.backend.domain.entities.TunableItem.TunableItem;
import com.imd.backend.domain.entities.TunableItem.TunableItemType;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.exception.NotFoundException;
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

  @Transactional(rollbackOn = Exception.class)
  public TuneetResume deleteById(UUID tuneetId) {
    final Optional<TuneetResume> findedTuneet = this.tuneetRepository.findById(tuneetId);

    if(findedTuneet.isEmpty())
      throw new NotFoundException("Não foi encontrado nenhum tuneet com esse ID");
    
    this.tuneetRepository.deleteById(tuneetId);
    return findedTuneet.get();
  }

  @Transactional(rollbackOn = Exception.class)
  public TuneetResume updateTuneet(
    UUID tuneetId,
    String textContent,
    String tunableItemId,
    TunableItemType tunableItemType
  ) {
    final TuneetResume tuneet = this.tuneetRepository.findById(tuneetId)
      .orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum tuneet com o ID fornecido"));
    
    final boolean hasTextContent = textContent != null && !textContent.isBlank();
    final boolean hasItemId = tunableItemId != null && !tunableItemId.isEmpty();
    final boolean hasItemType = tunableItemType != null;

    // Se tiver o tipo e o ID do item, mas não ambos juntos
    if(hasItemId ^ hasItemType) 
      throw new BusinessException("Para atualizar o item, é necessário passar o ID e o tipo dele");

    if(hasItemId && hasItemType) {
      final TunableItem tunableItem = this.plataformGateway.getItemById(tunableItemId, tunableItemType);
      tuneet.setTunableItem(tunableItem);
    }

    if(hasTextContent)
      tuneet.setTextContent(textContent);
    
    this.tuneetRepository.update(tuneet);
    return tuneet;
  }
}
