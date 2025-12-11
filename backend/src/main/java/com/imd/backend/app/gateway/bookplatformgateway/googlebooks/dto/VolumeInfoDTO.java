package com.imd.backend.app.gateway.bookplatformgateway.googlebooks.dto;

import java.util.List;

public record VolumeInfoDTO(
    String title,
    List<String> authors,
    String description,
    Integer pageCount,
    ImageLinksDTO imageLinks,
    List<IndustryIdentifierDTO> industryIdentifiers) {
}
