package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.strategy.searchitem;

import java.util.List;

import com.imd.backend.domain.valueobjects.bookitem.BookItem;

public interface SearchItemStrategy {
  public List<BookItem> execute(String query);
}
