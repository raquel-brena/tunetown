package com.imd.backend.domain.valueobjects.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.net.URI;

/**
 * PONTO FIXO (Value Object)
 * Representa os dados genéricos de um item de mídia externo.
 * Não é uma entidade de banco de dados.
 */
@Getter
@SuperBuilder // Permite que o builder da classe filha preencha estes campos
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class PostItem {
  private String id; // ID na plataforma externa
  private String title; // Título universal
  private String platformName; // "spotify", "netflix"
  private URI artworkUrl; // Capa

  // Método auxiliar útil para logs ou debug
  public String getSummary() {
    return String.format("[%s] %s (%s)", platformName, title, id);
  } 
}
