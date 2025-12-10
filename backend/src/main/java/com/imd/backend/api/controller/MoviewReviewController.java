package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BasePostController;
import com.imd.backend.app.dto.CreateTuneetDTO;
import com.imd.backend.app.dto.UpdateTuneetDTO;
import com.imd.backend.app.dto.movie.CreateMovieReviewDTO;
import com.imd.backend.app.dto.movie.UpdateMovieReviewDTO;
import com.imd.backend.app.service.MoviewReviewService;
import com.imd.backend.app.service.TuneetService;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;
import com.imd.backend.infra.security.CoreUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequestMapping("api/movie")
public class MoviewReviewController extends BasePostController<
        MovieReview,
        MovieItem,
        CreateMovieReviewDTO,
        UpdateMovieReviewDTO
> {
  public MoviewReviewController(MoviewReviewService service) {
    super(service);
  }

  @Override
  @PostMapping
  public ResponseEntity<MovieReview> create(
    @RequestBody @Validated CreateMovieReviewDTO dto,
    @AuthenticationPrincipal CoreUserDetails userDetails
  ) {
    return super.create(dto, userDetails);
  } // COLOQUEI ESSE MÉTODO PRA CÁ POIS ESTAVA DANDO PROBLEMA NO JACKSON COM O DTO GENÉRICO

  @Override
  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MovieReview> update(
    @PathVariable UUID id,
    @Validated @RequestBody UpdateMovieReviewDTO dto,
    @AuthenticationPrincipal CoreUserDetails userDetails 
  ) {
    return super.update(id, dto, userDetails);
  }  
}