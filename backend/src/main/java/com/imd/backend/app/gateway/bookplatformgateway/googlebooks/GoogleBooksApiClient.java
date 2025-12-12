package com.imd.backend.app.gateway.bookplatformgateway.googlebooks;

import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.dto.GoogleBookDetailDTO;
import com.imd.backend.app.gateway.bookplatformgateway.googlebooks.dto.GoogleBooksSearchResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
