package com.imd.backend.domain.valueObjects.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseTrendingItem<I extends PostItem> {
  private I item;
  private Long count;
}
