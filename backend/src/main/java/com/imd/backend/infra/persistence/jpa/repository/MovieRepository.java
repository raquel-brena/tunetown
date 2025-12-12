package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.domain.valueobjects.core.BaseResume;
import com.imd.backend.domain.valueobjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueobjects.core.BaseTrendingItem;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("MovieJpaRepository")
public interface MovieRepository extends BasePostRepository<MovieReview, MovieItem> {
  @Override
  @Query("SELECT m FROM MovieReview m WHERE m.movieId = :itemId")
  Page<MovieReview> findByItemId(@Param("itemId") String itemId, Pageable pageable);

  @Override
  @Query("SELECT m FROM MovieReview m WHERE UPPER(m.movieTitle) LIKE UPPER(CONCAT('%', :title, '%'))")
  Page<MovieReview> findByItemTitle(@Param("title") String title, Pageable pageable);

  @Override
  @Query("SELECT m FROM MovieReview m WHERE UPPER(m.movieDirector) LIKE UPPER(CONCAT('%', :creatorName, '%'))")
  Page<MovieReview> findByItemCreator(@Param("creatorName") String creatorName, Pageable pageable);

  @Override
  @Query("""
    SELECT NEW com.imd.backend.domain.valueobjects.core.BaseTrendingItem(
        NEW com.imd.backend.domain.valueobjects.movieitem.MovieItem(
            m.movieId,
            m.moviePlatform,
            m.movieTitle,
            m.movieArtworkUrl,
            m.movieDirector,
            m.movieReleaseYear,
            m.movieType
        ),
        COUNT(m)
    )
    FROM MovieReview m
    WHERE UPPER(m.movieType) = UPPER(:filterType)
    GROUP BY
        m.movieId,
        m.moviePlatform,
        m.movieTitle,
        m.movieArtworkUrl,
        m.movieDirector,
        m.movieReleaseYear,
        m.movieType
    ORDER BY COUNT(m) DESC
  """)
  List<BaseTrendingItem<MovieItem>> findTrendingItems(
      @Param("filterType") String filterType,
      Pageable pageable);

  @Override
  @Query("""
    SELECT NEW com.imd.backend.domain.valueobjects.core.BaseResume(
        m.id,
        m.textContent,
        m.createdAt,
        u.id,
        u.username,
        u.email,
        p.bio,
        f.fileName,
        SIZE(m.comments),
        SIZE(m.likes),
        SIZE(p.followers),
        SIZE(p.following),
        NEW com.imd.backend.domain.valueobjects.movieitem.MovieItem(
            m.movieId,
            m.moviePlatform,
            m.movieTitle,
            m.movieArtworkUrl,
            m.movieDirector,
            m.movieReleaseYear,
            m.movieType
        )
    )
    FROM MovieReview m
    JOIN m.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
  """)
  Page<BaseResume<MovieItem>> findAllResumes(Pageable pageable);

  @Override
  @Query("""
    SELECT NEW com.imd.backend.domain.valueobjects.core.BaseResume(
      m.id,
      m.textContent,
      m.createdAt,
      u.id,
      u.username,
      u.email,
      p.bio,
      f.fileName,
      SIZE(m.comments),
      SIZE(m.likes),
      SIZE(p.followers),
      SIZE(p.following),
      NEW com.imd.backend.domain.valueobjects.movieitem.MovieItem(
          m.movieId,
          m.moviePlatform,
          m.movieTitle,
          m.movieArtworkUrl,
          m.movieDirector,
          m.movieReleaseYear,
          m.movieType
      )
    )
    FROM MovieReview m
    JOIN m.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
    WHERE u.id = :authorId
  """)
  Page<BaseResume<MovieItem>> findResumesByAuthorId(@Param("authorId") String authorId, Pageable pageable);

  @Override
  @Query("""
  SELECT NEW com.imd.backend.domain.valueobjects.core.BaseResume(
      m.id,
      m.textContent,
      m.createdAt,
      u.id,
      u.username,
      u.email,
      p.bio,
      f.fileName,
      SIZE(m.comments),
      SIZE(m.likes),
      SIZE(p.followers),
      SIZE(p.following),
      NEW com.imd.backend.domain.valueobjects.movieitem.MovieItem(
        m.movieId,
        m.moviePlatform,
        m.movieTitle,
        m.movieArtworkUrl,
        m.movieDirector,
        m.movieReleaseYear,
        m.movieType
      )
    )
  FROM MovieReview m
  JOIN m.author u
  LEFT JOIN u.profile p
  LEFT JOIN p.photo f
  WHERE m.id = :id
  """)
  Optional<BaseResume<MovieItem>> findResumeById(@Param("id") String id);

  @Override
  @Query("""
    SELECT NEW com.imd.backend.domain.valueobjects.core.BaseTimelineItem(
      m.id, m.textContent, m.createdAt,
      SIZE(m.comments), SIZE(m.likes),
      u.id, u.username, f.url,
      NEW com.imd.backend.domain.valueobjects.movieitem.MovieItem(
        m.movieId,
        m.moviePlatform,
        m.movieTitle,
        m.movieArtworkUrl,
        m.movieDirector,
        m.movieReleaseYear,
        m.movieType
      )
    )
    FROM MovieReview m
    JOIN m.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
  """)
  Page<BaseTimelineItem<MovieItem>> findGlobalTimelineItems(Pageable pageable);

  @Override
  @Query("""
    SELECT NEW com.imd.backend.domain.valueobjects.core.BaseTimelineItem(
      m.id, m.textContent, m.createdAt,
      SIZE(m.comments), SIZE(m.likes),
      u.id, u.username, f.url,
      NEW com.imd.backend.domain.valueobjects.movieitem.MovieItem(
          m.movieId,
          m.moviePlatform,
          m.movieTitle,
          m.movieArtworkUrl,
          m.movieDirector,
          m.movieReleaseYear,
          m.movieType
      )
    )
    FROM MovieReview m
    JOIN m.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
    WHERE p IN (
      SELECT fol.followed FROM Profile p_curr JOIN p_curr.following fol
      WHERE p_curr.user.id = :currentUserId
    )
    OR u.id = :currentUserId
  """)
  Page<BaseTimelineItem<MovieItem>> findHomeTimelineItems(
      @Param("currentUserId") String currentUserId,
      Pageable pageable);
}
