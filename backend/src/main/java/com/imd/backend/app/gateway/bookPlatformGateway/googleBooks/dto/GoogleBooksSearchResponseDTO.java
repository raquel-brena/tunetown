package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.dto;

public record GoogleBooksSearchResponseDTO(
    List<GoogleBookDetailDTO> items) {
}
