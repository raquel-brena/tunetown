package com.imd.backend.domain.entities.filmLog;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "movie_reviews")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MovieReview extends BasePost {

  // --- DADOS DO ITEM (Achatados) ---
  @Column(name = "movie_id", nullable = false)
  private String movieId;

  @Column(name = "movie_title", nullable = false)
  private String movieTitle;

  @Column(name = "movie_platform", nullable = false)
  private String moviePlatform; // ex: "TMDB", "IMDB"

  @Column(name = "movie_artwork_url")
  private String movieArtworkUrl;

  // Campos Específicos de Filme
  @Column(name = "movie_director")
  private String movieDirector;

  @Column(name = "movie_release_year")
  private String movieReleaseYear;

  // --- DADO ESPECÍFICO DO POST (Não vem da API externa) ---
  @Column(name = "user_rating", nullable = false)
  private Integer rating; // 1 a 5 estrelas

  @OneToMany(mappedBy = "movieReview", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default
  @JsonIgnore
  private List<MovieComment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "movieReview", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @Builder.Default
  @JsonIgnore
  private List<MovieLike> likes = new ArrayList<>();

  // --- VALIDAÇÃO ESPECÍFICA ---
  public void validateMovieReview() {
    if (this.movieId == null || this.movieId.isBlank())
      throw new IllegalArgumentException("ID do filme é obrigatório");

    if (this.rating == null || this.rating < 1 || this.rating > 5)
      throw new IllegalArgumentException("A nota deve ser entre 1 e 5 estrelas.");
  }

  // --- HELPERS ---
  public MovieItem getMovieItem() {
    return MovieItem.builder()
        .id(this.movieId)
        .title(this.movieTitle)
        .platformName(this.moviePlatform)
        .director(this.movieDirector)
        .releaseYear(this.movieReleaseYear)
        .artworkUrl(this.movieArtworkUrl != null ? URI.create(this.movieArtworkUrl) : null)
        .build();
  }

  public void updateMovieItem(MovieItem item) {
    this.movieId = item.getId();
    this.movieTitle = item.getTitle();
    this.moviePlatform = item.getPlatformName();
    this.movieDirector = item.getDirector();
    this.movieReleaseYear = item.getReleaseYear();
    this.movieArtworkUrl = item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null;
  }

  // Para o Frontend
  @Transient
  @JsonProperty("totalComments")
  public int getTotalCommentsCount() {
    return comments != null ? comments.size() : 0;
  }

  @Transient
  @JsonProperty("totalLikes")
  public int getTotalLikesCount() {
    return likes != null ? likes.size() : 0;
  }

  @Override
    public String getContent() {
        return String.format("%s (%s) - Dir. %s [%d/5]", 
            this.movieTitle, this.movieReleaseYear, this.movieDirector, this.rating);
    }
}
