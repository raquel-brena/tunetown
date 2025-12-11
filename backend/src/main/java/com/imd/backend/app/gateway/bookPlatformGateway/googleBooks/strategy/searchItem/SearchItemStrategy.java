package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.searchItem;

import java.util.List;

import com.imd.backend.domain.valueObjects.bookItem.BookItem;

public interface SearchItemStrategy {
  public List<BookItem> execute(String query);
}
