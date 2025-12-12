package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BasePostController;
import com.imd.backend.app.dto.movie.CreateMovieReviewDTO;
import com.imd.backend.app.dto.movie.UpdateMovieReviewDTO;
import com.imd.backend.app.service.MovieService;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;
import com.imd.backend.infra.security.CoreUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/filmlog")
public class MovieController extends BasePostController<
  MovieReview,
  MovieItem,
  CreateMovieReviewDTO,
  UpdateMovieReviewDTO
> {

  public MovieController(MovieService service) {
    super(service);
  }

  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MovieReview> create(
      @Validated @RequestBody CreateMovieReviewDTO dto,
      @AuthenticationPrincipal CoreUserDetails userDetails) {
    return super.create(dto, userDetails);
  }

  @Override
  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MovieReview> update(
      @PathVariable UUID id,
      @Validated @RequestBody UpdateMovieReviewDTO dto,
      @AuthenticationPrincipal CoreUserDetails userDetails) {
    return super.update(id, dto, userDetails);
  }
}
