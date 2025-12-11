package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.searchItem;

import java.util.List;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.GoogleBooksApiClient;
import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.mapper.BookItemGoogleMapper;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SearchBookStrategy implements SearchItemStrategy{
  private final GoogleBooksApiClient apiClient;
  private final BookItemGoogleMapper mapper;  

  public List<BookItem> execute(String query) {
    var response = apiClient.searchBooks(query, 20, "books");
    
    if (response.items() == null) return List.of();

    return response.items().stream()
      .map(mapper::fromGoogleVolume)
      .toList();
  }  
}
