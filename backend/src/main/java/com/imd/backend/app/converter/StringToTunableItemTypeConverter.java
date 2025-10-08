package com.imd.backend.app.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

@Component
public class StringToTunableItemTypeConverter implements Converter<String, TunableItemType>{
  @Override
  public TunableItemType convert(String source) {
    return TunableItemType.fromString(source);
  }
}
