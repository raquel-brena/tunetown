package com.imd.backend.infra.persistence.jpa.repository.bookYard;

import com.imd.backend.domain.entities.bookYard.BookReview;
import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("BookJpaRepository")
public interface BookReviewRepository extends BasePostRepository<BookReview, BookItem> {
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
              NEW com.imd.backend.domain.valueObjects.bookItem.BookItem(
              m.bookId,
                  m.bookPlatform,
                  m.bookTitle,
                  m.bookArtworkUrl,
                  m.bookAuthor,
                  m.bookIsbn,
                  m.bookPageCount
              )
          )
          FROM BookReview m
          JOIN m.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE u.id = :authorId
      """)
    Page<BaseResume<BookItem>> findResumesByAuthorId(@Param("authorId") String authorId, Pageable pageable);

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
                  NEW com.imd.backend.domain.valueObjects.bookItem.BookItem(
                   t.bookId,
                  t.bookPlatform,
                  t.bookTitle,
                  t.bookArtworkUrl,
                  t.bookAuthor,
                  t.bookIsbn,
                  t.bookPageCount
              )
          )
          FROM BookReview t
          JOIN t.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE t.id = :id
      """)
    Optional<BaseResume<BookItem>> findResumeById(@Param("id") String id);

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
                  NEW com.imd.backend.domain.valueObjects.bookItem.BookItem(
                  t.bookId,
                  t.bookPlatform,
                  t.bookTitle,
                  t.bookArtworkUrl,
                  t.bookAuthor,
                  t.bookIsbn,
                  t.bookPageCount
              )
      )
        FROM BookReview t
        JOIN t.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
    """)
    Page<BaseResume<BookItem>> findAllResumes(Pageable pageable);

    Page<BookReview> findByMovieTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<BookReview> findByMovieDirectorContainingIgnoreCase(String name, Pageable pageable);

    @Override
    @Query("""
        SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTrendingItem(
            NEW com.imd.backend.domain.valueObjects.bookItem.BookItem(
               t.bookId,
                  t.bookPlatform,
                  t.bookTitle,
                  t.bookArtworkUrl,
                  t.bookAuthor,
                  t.bookIsbn,
                  t.bookPageCount
            ),
            COUNT(t)
        )
        FROM BookReview t
        WHERE UPPER(t.bookAuthor) = UPPER(:filterDirector)
        GROUP BY 
       t.bookId,
                  t.bookPlatform,
                  t.bookTitle,
                  t.bookArtworkUrl,
                  t.bookAuthor,
                  t.bookIsbn,
                  t.bookPageCount
        ORDER BY COUNT(t) DESC
    """)
    List<BaseTrendingItem<BookItem>> findTrendingItems(
        @Param("filterDirector") String filterDirector,
        Pageable pageable
    );

    Page<BookReview> findByAuthorId(String authorId, Pageable pageable);

    @Query("""
      SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTimelineItem(
        t.id, t.textContent, t.createdAt,
        SIZE(t.comments), SIZE(t.likes),
        u.id, u.username, f.url, 
        NEW com.imd.backend.domain.valueObjects.bookItem.BookItem(
         t.bookId,
                  t.bookPlatform,
                  t.bookTitle,
                  t.bookArtworkUrl,
                  t.bookAuthor,
                  t.bookIsbn,
                  t.bookPageCount
        )
      )
      FROM BookReview t
      JOIN t.author u
      LEFT JOIN u.profile p
      LEFT JOIN p.photo f
    """)
    Page<BaseTimelineItem<BookItem>> findGlobalTimelineItems(Pageable pageable);

    @Query("""
      SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTimelineItem(
        t.id, t.textContent, t.createdAt,
        SIZE(t.comments), SIZE(t.likes),
        u.id, u.username, f.url,
        NEW com.imd.backend.domain.valueObjects.bookItem.BookItem(
       t.bookId,
                  t.bookPlatform,
                  t.bookTitle,
                  t.bookArtworkUrl,
                  t.bookAuthor,
                  t.bookIsbn,
                  t.bookPageCount
        )
      )
      FROM BookReview t
      JOIN t.author u
      LEFT JOIN u.profile p
      LEFT JOIN p.photo f
      WHERE p IN (
        SELECT fol.followed FROM Profile p_curr JOIN p_curr.following fol
        WHERE p_curr.user.id = :currentUserId
      )
      OR u.id = :currentUserId
    """)
    Page<BaseTimelineItem<BookItem>> findHomeTimelineItems(
      @Param("currentUserId") String currentUserId,
      Pageable pageable
    );

    public Page<BookReview> findAll(Pageable pagination);

    @Override
    @Query("SELECT t FROM BookReview t WHERE t.bookId = :itemId")
    Page<BookReview> findByItemId(@Param("itemId") String itemId, Pageable pageable);

    @Override
    @Query("SELECT t FROM BookReview t WHERE UPPER(t.bookTitle) LIKE UPPER(CONCAT('%', :title, '%'))")
    Page<BookReview> findByItemTitle(@Param("title") String title, Pageable pageable);

    @Override
    @Query("SELECT t FROM BookReview t WHERE UPPER(t.bookAuthor) LIKE UPPER(CONCAT('%', :creatorName, '%'))")
    Page<BookReview> findByItemCreator(@Param("creatorName") String creatorName, Pageable pageable);

    @Query("""
    SELECT TimeLineItem(
        t.id,
        t.textContent,
        t.createdAt,
        t.author.id,
        t.author.username,
        t.bookTitle,
        t.bookAuthor,
        t.bookArtworkUrl
    )
    FROM BookReview t
    ORDER BY t.createdAt DESC
""")
    public Page<TimeLineItem> getGlobalTimeline(Pageable pagination);

    @Query("""
    SELECT t
    FROM BookReview t
    WHERE t.author.id = :authorId
    ORDER BY t.createdAt DESC
""")
    public Page<TimeLineItem> getHomeTimeline(String currentUserId, Pageable pagination);
}
