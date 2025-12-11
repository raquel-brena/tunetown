package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.strategy.getitembyid;

import com.imd.backend.domain.valueobjects.bookitem.BookItem;

public interface GetItemByIdStrategy {
  public BookItem execute(String id);
}
