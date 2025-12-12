package com.imd.backend.app.gateway.bookplatformgateway;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;

import java.util.List;

public interface BookPlatformGateway extends IPostItemGateway<BookItem> {
  List<BookItem> searchItem(String query, String itemType);

  BookItem getItemById(String id, String itemType);
}
