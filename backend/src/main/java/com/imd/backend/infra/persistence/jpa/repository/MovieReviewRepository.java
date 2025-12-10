package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("MovieJpaRepository")
public interface MovieReviewRepository extends BasePostRepository<MovieReview, MovieItem> {
  @Override
  @Query("""
          SELECT NEW com.imd.backend.domain.valueObjects.core.BaseResume(
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
              NEW com.imd.backend.domain.valueObjects.movieItem.MovieItem(
                  m.movieId,
                  m.moviePlatform,
                  m.movieTitle,
                  m.movieArtworkUrl,
                  m.movieDirector,
                  m.movieReleaseYear,
                  m.rating
              )
          )
          FROM MovieReview m
          JOIN m.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE u.id = :authorId
      """)
    Page<BaseResume<MovieItem>> findResumesByAuthorId(@Param("authorId") String authorId, Pageable pageable);

      @Query("""
          SELECT NEW com.imd.backend.domain.valueObjects.core.BaseResume(
              t.id,
              t.textContent,
              t.createdAt,
              u.id,
              u.username,
              u.email,
              p.bio,
              f.fileName,
              SIZE(t.comments),
              SIZE(t.likes),
              SIZE(p.followers),
              SIZE(p.following),
                  NEW com.imd.backend.domain.valueObjects.movieItem.MovieItem(
                  t.movieId,
                  t.moviePlatform,
                  t.movieTitle,
                  t.movieArtworkUrl,
                  t.movieDirector,
                  t.movieReleaseYear,
                  t.rating
              )
          )
          FROM MovieReview t
          JOIN t.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE t.id = :id
      """)
    Optional<BaseResume<MovieItem>> findResumeById(@Param("id") String id);

    @Override
    @Query("""
      SELECT NEW com.imd.backend.domain.valueObjects.core.BaseResume(
          t.id,
          t.textContent,
          t.createdAt,
          u.id,
          u.username,
          u.email,
          p.bio,
          f.fileName,
          SIZE(t.comments),
          SIZE(t.likes),
          SIZE(p.followers),
          SIZE(p.following),
                  NEW com.imd.backend.domain.valueObjects.movieItem.MovieItem(
                  t.movieId,
                  t.moviePlatform,
                  t.movieTitle,
                  t.movieArtworkUrl,
                  t.movieDirector,
                  t.movieReleaseYear,
                  t.rating
              )
      )
        FROM MovieReview t
        JOIN t.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
    """)
    Page<BaseResume<MovieItem>> findAllResumes(Pageable pageable);

    Page<MovieReview> findByMovieTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<MovieReview> findByMovieDirectorContainingIgnoreCase(String name, Pageable pageable);

    @Override
    @Query("""
        SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTrendingItem(
            NEW com.imd.backend.domain.valueObjects.movieItem.MovieItem(
                  t.movieId,
                  t.moviePlatform,
                  t.movieTitle,
                  t.movieArtworkUrl,
                  t.movieDirector,
                  t.movieReleaseYear,
                  t.rating
            ),
            COUNT(t)
        )
        FROM MovieReview t
        WHERE UPPER(t.movieDirector) = UPPER(:filterDirector)
        GROUP BY 
         t.movieId,
                  t.moviePlatform,
                  t.movieTitle,
                  t.movieArtworkUrl,
                  t.movieDirector,
                  t.movieReleaseYear,
                  t.rating
        ORDER BY COUNT(t) DESC
    """)
    List<BaseTrendingItem<MovieItem>> findTrendingItems(
        @Param("filterDirector") String filterDirector,
        Pageable pageable
    );

    Page<MovieReview> findByAuthorId(String authorId, Pageable pageable);

    @Query("""
      SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTimelineItem(
        t.id, t.textContent, t.createdAt,
        SIZE(t.comments), SIZE(t.likes),
        u.id, u.username, f.url, 
        NEW com.imd.backend.domain.valueObjects.movieItem.MovieItem(
             t.movieId,
                  t.moviePlatform,
                  t.movieTitle,
                  t.movieArtworkUrl,
                  t.movieDirector,
                  t.movieReleaseYear,
                  t.rating
        )
      )
      FROM MovieReview t
      JOIN t.author u
      LEFT JOIN u.profile p
      LEFT JOIN p.photo f
    """)
    Page<BaseTimelineItem<MovieItem>> findGlobalTimelineItems(Pageable pageable);

    @Query("""
      SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTimelineItem(
        t.id, t.textContent, t.createdAt,
        SIZE(t.comments), SIZE(t.likes),
        u.id, u.username, f.url,
        NEW com.imd.backend.domain.valueObjects.movieItem.MovieItem(
          t.movieId,
                  t.moviePlatform,
                  t.movieTitle,
                  t.movieArtworkUrl,
                  t.movieDirector,
                  t.movieReleaseYear,
                  t.rating
        )
      )
      FROM MovieReview t
      JOIN t.author u
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
      Pageable pageable
    );

    public Page<MovieReview> findAll(Pageable pagination);

    @Override
    @Query("SELECT t FROM MovieReview t WHERE t.movieId = :itemId")
    Page<MovieReview> findByItemId(@Param("itemId") String itemId, Pageable pageable);

    @Override
    @Query("SELECT t FROM MovieReview t WHERE UPPER(t.movieTitle) LIKE UPPER(CONCAT('%', :title, '%'))")
    Page<MovieReview> findByItemTitle(@Param("title") String title, Pageable pageable);

    @Override
    @Query("SELECT t FROM MovieReview t WHERE UPPER(t.movieDirector) LIKE UPPER(CONCAT('%', :creatorName, '%'))")
    Page<MovieReview> findByItemCreator(@Param("creatorName") String creatorName, Pageable pageable);

    @Query("""
    SELECT TimeLineItem(
        t.id,
        t.textContent,
        t.createdAt,
        t.author.id,
        t.author.username,
        t.movieTitle,
        t.movieDirector,
        t.movieArtworkUrl
    )
    FROM MovieReview t
    ORDER BY t.createdAt DESC
""")
    public Page<TimeLineItem> getGlobalTimeline(Pageable pagination);

    @Query("""
    SELECT t
    FROM MovieReview t
    WHERE t.author.id = :authorId
    ORDER BY t.createdAt DESC
""")
    public Page<TimeLineItem> getHomeTimeline(String currentUserId, Pageable pagination);
}
