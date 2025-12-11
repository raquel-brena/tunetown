package com.imd.backend.app.gateway.bookPlatformGateway.googleBooks;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.dto.GoogleBookDetailDTO;
import com.imd.backend.app.gateway.bookPlatformGateway.googleBooks.dto.GoogleBooksSearchResponseDTO;

@FeignClient(name = "GoogleBooksApiClient", url = "https://www.googleapis.com/books/v1")
public interface GoogleBooksApiClient {

  @GetMapping("/volumes")
  GoogleBooksSearchResponseDTO searchBooks(
      @RequestParam("q") String query,
      @RequestParam(value = "maxResults", defaultValue = "10") int maxResults,
      @RequestParam(value = "printType", defaultValue = "books") String printType);

  @GetMapping("/volumes/{id}")
  GoogleBookDetailDTO getBookById(@PathVariable("id") String id);
}
