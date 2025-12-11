package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.factories;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.getItemById.GetBookByIdStrategy;
import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.getItemById.GetItemByIdStrategy;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueObjects.bookItem.BookItemType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleBooksGetByIdFactory {
  private final GetBookByIdStrategy bookStrategy;

  public GetItemByIdStrategy create(BookItemType type) {
    if (type == BookItemType.BOOK) {
      return bookStrategy;
    }
    throw new BusinessException("Tipo de item não suportado para busca de livros");
  }
}
