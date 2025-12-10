package com.imd.backend.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imd.backend.app.dto.core.CreateBasePostDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBookReviewDTO extends CreateBasePostDTO {
  @NotBlank(message = "O status de leitura é obrigatório")
  @JsonProperty("readingStatus")
  private String readingStatus; // Ex: READING, COMPLETED, WANT_TO_READ
}
