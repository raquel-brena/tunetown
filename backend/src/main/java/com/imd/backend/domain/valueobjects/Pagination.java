package com.imd.backend.domain.valueobjects;

public record Pagination (
    int page,
    int size,
    String orderBy,
    String orderDirection) 
  {
  public static final int DEFAULT_PAGE_SIZE = 20;

  public Pagination {
    if (size <= 0) size = DEFAULT_PAGE_SIZE;
    if (page < 0) page = 0;

    if(
      orderDirection == null || !(orderDirection.equals("ASC") || orderDirection.equals("DESC"))
    )
      orderDirection = "ASC";
  }
}
