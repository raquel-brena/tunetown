package com.imd.backend.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imd.backend.app.dto.core.CreateBasePostDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMovieReviewDTO extends CreateBasePostDTO {
  @NotNull(message = "A nota é obrigatória")
  @Min(value = 1, message = "A nota mínima é 1")
  @Max(value = 5, message = "A nota máxima é 5")
  @JsonProperty("rating")
  private Integer rating;
}
