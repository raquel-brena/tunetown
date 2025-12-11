package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.strategy.getItemById;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.GoogleBooksApiClient;
import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.mapper.BookItemGoogleMapper;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetBookByIdStrategy implements GetItemByIdStrategy{
  private final GoogleBooksApiClient apiClient;
  private final BookItemGoogleMapper mapper;

    public BookItem execute(String id) {
        var response = apiClient.getBookById(id);
        return mapper.fromGoogleVolume(response);
    }  
}
