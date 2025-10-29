package com.imd.backend.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.repository.TuneetRepository;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

import jakarta.transaction.Transactional;

@Service
public class TuneetService {
  private final TunablePlataformGateway plataformGateway;
  private final TuneetRepository tuneetRepository;
  private final UserService userService;

  public TuneetService(
    @Qualifier("SpotifyGateway") TunablePlataformGateway plataformGateway,
    @Qualifier("TuneetJpaRepository") TuneetRepository tuneetRepository,
    UserService userService,
    ProfileService profileService) {
    this.plataformGateway = plataformGateway;
    this.tuneetRepository = tuneetRepository;
    this.userService = userService;
  }

  public Optional<Tuneet> findTuneetById(UUID id) {
    return this.tuneetRepository.findById(id);
  }

  public PageResult<Tuneet> findAllTuneets(Pagination pagination) {
    return this.tuneetRepository.findAll(pagination);
  }

  public PageResult<Tuneet> findTuneetsByAuthorId(String authorId, Pagination pagination) {
    if(this.userService.findUserByUsername(authorId) == null)
      throw new NotFoundException("Não existe nenhum autor com esse username");

    return this.tuneetRepository.findByAuthorId(authorId, pagination);
  }

  public PageResult<Tuneet> findTuneetByTunableItem(
    String tunableItemId, 
    Pagination pagination
  ) {    
    return this.tuneetRepository.findByTunableItemId(tunableItemId, pagination);
  }

  public PageResult<Tuneet> findTuneetByTunableItemTitleContaining(String word, Pagination pagination) {
    return this.tuneetRepository.findByTunableItemTitleContaining(word, pagination);
  }

  public PageResult<Tuneet> findTuneetByTunableItemArtistContaining(String word, Pagination pagination) {
    return this.tuneetRepository.findByTunableItemArtistContaining(word, pagination);
  }  

  public List<TunableItem> searchTunableItems(String query, TunableItemType itemType) {
    return plataformGateway.searchItem(query, itemType);
  } 

  public List<TrendingTuneResult> getTrendingTunes(TunableItemType type,int limit) {
    return this.tuneetRepository.findTrendingTunesByType(type, limit);
  }

  @Transactional(rollbackOn = Exception.class)
  public Tuneet createTuneet(
    String tunableItemId,
    UserEntity user,
    TunableItemType tunableItemType,
    String textContent    
  ) {
    if(!this.userService.userExistsById(user.getId().toString())) // Se o usuário não existir
      throw new BusinessException("Não existe nenhum usuário com esse ID");

    final TunableItem tunableItem = this.plataformGateway.getItemById(tunableItemId, tunableItemType);
    final Tuneet tuneetToSave = Tuneet.createNew(user, textContent, tunableItem);

    this.tuneetRepository.save(tuneetToSave);
    return tuneetToSave;
  }

  @Transactional(rollbackOn = Exception.class)
  public Tuneet deleteById(UUID tuneetId) {
    final Optional<Tuneet> findedTuneet = this.tuneetRepository.findById(tuneetId);

    if(findedTuneet.isEmpty())
      throw new NotFoundException("Não foi encontrado nenhum tuneet com esse ID");
    
    this.tuneetRepository.deleteById(tuneetId);
    return findedTuneet.get();
  }

  @Transactional(rollbackOn = Exception.class)
  public Tuneet updateTuneet(
    UUID tuneetId,
    String textContent,
    String tunableItemId,
    TunableItemType tunableItemType
  ) {
    final Tuneet tuneet = this.tuneetRepository.findById(tuneetId)
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
