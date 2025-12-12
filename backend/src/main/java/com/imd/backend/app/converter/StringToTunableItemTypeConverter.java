package com.imd.backend.app.converter;

import com.imd.backend.domain.valueobjects.tunableitem.TunableItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTunableItemTypeConverter implements Converter<String, TunableItemType>{
  @Override
  public TunableItemType convert(String source) {
    return TunableItemType.fromString(source);
  }
}
