package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.factories;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.searchItem.SearchBookStrategy;
import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.searchItem.SearchItemStrategy;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueObjects.bookItem.BookItemType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleBooksSearchFactory {
  private final SearchBookStrategy bookStrategy;

  public SearchItemStrategy create(BookItemType type) {
    if (type == BookItemType.BOOK) {
      return bookStrategy;
    }
    
    throw new BusinessException("Tipo de item não suportado para busca de livros");
  }
}
