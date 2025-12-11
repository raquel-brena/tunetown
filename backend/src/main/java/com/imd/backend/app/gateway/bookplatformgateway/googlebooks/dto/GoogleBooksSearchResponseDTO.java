package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.dto;

import java.util.List;

public record GoogleBooksSearchResponseDTO(
    List<GoogleBookDetailDTO> items
) {}
