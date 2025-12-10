package com.imd.backend.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imd.backend.app.dto.core.UpdateBasePostDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBookReviewDTO extends UpdateBasePostDTO {
  @JsonProperty("readingStatus")
  private String readingStatus;
}
