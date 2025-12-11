package com.imd.backend.app.gateway.bookplatformgateway;

import java.util.List;

import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;

public interface BookPlatformGateway extends IPostItemGateway<BookItem> {
  List<BookItem> searchItem(String query, String itemType);

  BookItem getItemById(String id, String itemType);
}
