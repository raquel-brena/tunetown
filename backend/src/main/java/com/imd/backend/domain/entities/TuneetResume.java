package com.imd.backend.domain.entities;

import java.util.UUID;

import com.imd.backend.domain.entities.TunableItem.TunableItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TuneetResume {
  private UUID id;
  private String textContent;
  private TunableItem tunableItem;
}
