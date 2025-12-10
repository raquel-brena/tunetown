package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.getItemById;

import com.imd.backend.domain.valueObjects.bookItem.BookItem;

public interface GetItemByIdStrategy {
  public BookItem execute(String id);
}
