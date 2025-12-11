package com.imd.backend.app.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.imd.backend.app.dto.movie.CreateMovieReviewDTO;
import com.imd.backend.app.dto.movie.UpdateMovieReviewDTO;
import com.imd.backend.app.gateway.filmplataformgateway.FilmPlatformGateway;
import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.domain.valueobjects.core.BaseResume;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.MovieRepository;

@Service
public class MovieService extends BasePostService<
  MovieReview, 
  MovieItem, 
  CreateMovieReviewDTO,
  UpdateMovieReviewDTO
> {
  private final FileService fileService;

  public MovieService(
      @Qualifier("MovieJpaRepository") MovieRepository repository,
      UserService userService,
      @Qualifier("TmdbGateway") FilmPlatformGateway mediaGateway,
      FileService fileService) {
    super(repository, userService, mediaGateway);
    this.fileService = fileService;
  }

  @Override
  protected MovieReview createEntityInstance(User author, CreateMovieReviewDTO dto, MovieItem item) {
    return MovieReview.builder()
        // Dados do BasePost
        .id(UUID.randomUUID().toString())
        .author(author)
        .textContent(dto.getTextContent())
        .createdAt(LocalDateTime.now())

        .movieId(item.getId())
        .moviePlatform(item.getPlatformName())
        .movieTitle(item.getTitle())
        .movieDirector(item.getDirector()) // Showrunner ou Diretor
        .movieReleaseYear(item.getReleaseYear())
        .movieType(item.getItemType().toString()) // Salva se é MOVIE ou SERIES
        .movieArtworkUrl(item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null)

        // Dados Específicos do DTO (Rating)
        .rating(dto.getRating())
        .build();
  }

  @Override
  protected void updateEntityInstance(MovieReview entity, UpdateMovieReviewDTO dto, MovieItem newItem) {
    // Texto já é atualizado pelo pai

    if (newItem != null) {
      entity.updateMovieItem(newItem);
    }

    if (dto.getRating() != null) {
      entity.setRating(dto.getRating());
    }
  }

  @Override
  protected void validateSpecificEntity(MovieReview entity) {
    entity.validateMovieReview();
  }

  @Override
  protected void postProcessResume(BaseResume<MovieItem> resume) {
    if (resume.getAuthorPhotoFileName() != null && !resume.getAuthorPhotoFileName().isBlank()) {
      String presignedUrl = fileService.applyPresignedUrl(resume.getAuthorPhotoFileName());
      resume.setAuthorPhotoUrl(presignedUrl);
    }
  }
}
