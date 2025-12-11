package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.strategy.searchitem;

import java.util.List;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.GoogleBooksApiClient;
import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.mapper.BookItemGoogleMapper;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;

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
