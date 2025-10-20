package com.imd.backend.domain.valueObjects;

import java.util.List;

public record PageResult<T> (
    List<T> itens,
    int pageItens,
    long totalItens,
    int currentPage,
    int pageSize,
    int totalPages) {}
