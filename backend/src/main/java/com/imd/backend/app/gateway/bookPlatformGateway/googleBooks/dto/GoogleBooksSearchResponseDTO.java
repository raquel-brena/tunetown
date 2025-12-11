package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.dto;

import java.util.List;

public record GoogleBooksSearchResponseDTO(
    List<GoogleBookDetailDTO> items
) {}
