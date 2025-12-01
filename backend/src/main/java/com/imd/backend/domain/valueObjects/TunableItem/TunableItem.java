package com.imd.backend.domain.valueObjects.TunableItem;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.net.URI;

import com.imd.backend.domain.valueObjects.core.PostItem;

@Getter
@Setter
@SuperBuilder // ESSENCIAL: Substitui @Builder para funcionar com herança
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // Importante: compara também os campos do Pai (ID, Title, etc)
public final class TunableItem extends PostItem {
  private String artist;
  private TunableItemType itemType;

  public TunableItem(
      String id,
      String plataformName, // Mapeia para o 'platformName' do pai
      String title,
      String artist,
      URI artworkUrl,
      TunableItemType itemType
  ) {
      // Chama o construtor do pai (PostItem)
      super(id, title, plataformName, artworkUrl);
      
      // Inicializa os campos específicos
      this.artist = artist;
      this.itemType = itemType;
  }    
}
