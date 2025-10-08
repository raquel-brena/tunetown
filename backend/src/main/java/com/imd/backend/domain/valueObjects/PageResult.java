package com.imd.backend.domain.valueObjects;

import java.util.List;

public record PageResult<T> (
    List<T> itens,
    int currentPage,
    int pageSize,
    long itensCount,
    int totalPages) {}
