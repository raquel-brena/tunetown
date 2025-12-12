package com.imd.backend.domain.valueobjects.tunableitem;

import com.imd.backend.domain.valueobjects.core.PostItem;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.net.URI;

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

  // --- NOVO CONSTRUTOR "JPA COMPLIANT" ---
  // Este construtor aceita Strings para URL e Enum e faz a conversão.
  // É usado exclusivamente pela query JPQL.
  public TunableItem(
      String id,
      String platformName,
      String title,
      String artist,
      String artworkUrlStr, // Recebe String do banco
      String itemTypeStr // Recebe String do banco
  ) {
    super(
        id,
        title,
        platformName,
        artworkUrlStr != null ? URI.create(artworkUrlStr) : null // Converte aqui
    );
    this.artist = artist;
    this.itemType = TunableItemType.fromString(itemTypeStr); // Converte aqui
  }  
}
