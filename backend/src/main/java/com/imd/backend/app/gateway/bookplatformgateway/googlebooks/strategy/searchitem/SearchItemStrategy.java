package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.strategy.searchitem;

import com.imd.backend.domain.valueobjects.bookitem.BookItem;

import java.util.List;

public interface SearchItemStrategy {
  public List<BookItem> execute(String query);
}
