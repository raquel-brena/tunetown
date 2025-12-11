package com.imd.backend.api.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imd.backend.api.controller.core.BasePostController;
import com.imd.backend.app.dto.bookYard.CreateBookReviewDTO;
import com.imd.backend.app.dto.bookYard.UpdateBookReviewDTO;
import com.imd.backend.app.service.BookService;
import com.imd.backend.domain.entities.bookYard.BookReview;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;
import com.imd.backend.infra.security.CoreUserDetails;

@RestController
@RequestMapping("api/bookyard")
public class BookController extends BasePostController<
 BookReview,
 BookItem,
 CreateBookReviewDTO,
 UpdateBookReviewDTO
>{
  public BookController(BookService service) {
    super(service);
  }

  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookReview> create(
      @Validated @RequestBody CreateBookReviewDTO dto,
      @AuthenticationPrincipal CoreUserDetails userDetails) {
    return super.create(dto, userDetails);
  }

  @Override
  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookReview> update(
      @PathVariable UUID id,
      @Validated @RequestBody UpdateBookReviewDTO dto,
      @AuthenticationPrincipal CoreUserDetails userDetails) {
    return super.update(id, dto, userDetails);
  }  
}
