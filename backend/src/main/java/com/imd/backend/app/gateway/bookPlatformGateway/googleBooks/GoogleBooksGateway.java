package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.bookPlatformGateway.BookPlatformGateway;
import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.factories.GoogleBooksGetByIdFactory;
import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.factories.GoogleBooksSearchFactory;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;
import com.imd.backend.domain.valueObjects.bookItem.BookItemType;

import lombok.RequiredArgsConstructor;

@Component
@Qualifier("GoogleBooksGateway")
@RequiredArgsConstructor
public class GoogleBooksGateway implements BookPlatformGateway {

  private final GoogleBooksSearchFactory searchFactory;
  private final GoogleBooksGetByIdFactory byIdFactory;

  @Override
  public List<BookItem> searchItem(String query, String itemType) {
    BookItemType type = BookItemType.fromString(itemType);
    return searchFactory.create(type).execute(query);
  }

  @Override
  public BookItem getItemById(String id, String itemType) {
    BookItemType type = BookItemType.fromString(itemType);
    return byIdFactory.create(type).execute(id);
  }
}
