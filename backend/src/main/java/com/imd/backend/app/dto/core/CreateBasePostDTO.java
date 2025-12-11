package com.imd.backend.app.dto.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // <- cria setters públicos
@NoArgsConstructor
@AllArgsConstructor
public class CreateBasePostDTO {

  @NotBlank(message = "O conteúdo do texto é obrigatório")
  @JsonProperty("textContent")
  private String textContent;

  @NotBlank(message = "O ID do item é obrigatório")
  @JsonProperty("itemId")
  private String itemId;

  @NotBlank(message = "O tipo do item é obrigatório")
  @JsonProperty("itemType")
  private String itemType;
}
