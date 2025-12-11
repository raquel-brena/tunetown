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
import com.imd.backend.app.dto.movie.CreateMovieReviewDTO;
import com.imd.backend.app.dto.movie.UpdateMovieReviewDTO;
import com.imd.backend.app.service.MovieService;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;
import com.imd.backend.infra.security.CoreUserDetails;

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
