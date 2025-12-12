package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.factories;

import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.strategy.searchitem.SearchBookStrategy;
import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.strategy.searchitem.SearchItemStrategy;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.valueobjects.bookitem.BookItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
