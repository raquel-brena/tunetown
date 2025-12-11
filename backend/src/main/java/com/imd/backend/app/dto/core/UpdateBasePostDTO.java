package com.imd.backend.app.dto.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
public class UpdateBasePostDTO {
  private String textContent;
  private String itemId;
  private String itemType;  
}
