package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.strategy.getitembyid;

import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.GoogleBooksApiClient;
import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.mapper.BookItemGoogleMapper;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;

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
