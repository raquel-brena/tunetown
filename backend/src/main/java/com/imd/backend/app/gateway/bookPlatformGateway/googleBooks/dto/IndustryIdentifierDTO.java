package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.dto;

public record IndustryIdentifierDTO(
    String type, // ex: "ISBN_13"
    String identifier // ex: "9781234567890"
) {}
