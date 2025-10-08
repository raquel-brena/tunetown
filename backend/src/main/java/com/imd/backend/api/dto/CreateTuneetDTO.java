package com.imd.backend.api.dto;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTuneetDTO (
  @NotBlank(message = "O conteúdo de texto do tuneet não pode estar vazio") String textContent,
  // @NotBlank(message = "O nome da plataforma do item tunetável deve ser preenchida") String itemPlataform,
  @NotBlank(message = "ID do item tunetável") String itemId,
  @NotNull(message = "O tipo do item tunetável deve ser adicionado") TunableItemType itemType
) {}
