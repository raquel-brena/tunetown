package com.imd.backend.api.controller.bookYard;

import com.imd.backend.api.controller.core.BasePostController;
import com.imd.backend.app.dto.bookYard.CreateBookReviewDTO;
import com.imd.backend.app.dto.bookYard.UpdateBookReviewDTO;
import com.imd.backend.app.service.bookYard.BookReviewService;
import com.imd.backend.domain.entities.bookYard.BookReview;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;
import com.imd.backend.infra.security.CoreUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/filmlog/reviews")
public class BookReviewController extends BasePostController<
        BookReview,
        BookItem,
        CreateBookReviewDTO,
        UpdateBookReviewDTO
> {
  public BookReviewController(BookReviewService service) {
    super(service);
  }

  @Override
  @PostMapping
  public ResponseEntity<BookReview> create(
    @RequestBody @Validated CreateBookReviewDTO dto,
    @AuthenticationPrincipal CoreUserDetails userDetails
  ) {
    return super.create(dto, userDetails);
  } // COLOQUEI ESSE MÉTODO PRA CÁ POIS ESTAVA DANDO PROBLEMA NO JACKSON COM O DTO GENÉRICO

  @Override
  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookReview> update(
    @PathVariable UUID id,
    @Validated @RequestBody UpdateBookReviewDTO dto,
    @AuthenticationPrincipal CoreUserDetails userDetails 
  ) {
    return super.update(id, dto, userDetails);
  }  
}