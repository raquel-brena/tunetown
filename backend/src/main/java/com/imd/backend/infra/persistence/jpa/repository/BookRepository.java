package com.imd.backend.infra.persistence.jpa.repository;

import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;
import com.imd.backend.domain.valueobjects.core.BaseResume;
import com.imd.backend.domain.valueobjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueobjects.core.BaseTrendingItem;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("BookJpaRepository")
public interface BookRepository extends BasePostRepository<BookReview, BookItem> {
  @Override
  @Query("SELECT b FROM BookReview b WHERE b.bookId = :itemId")
  Page<BookReview> findByItemId(@Param("itemId") String itemId, Pageable pageable);

  @Override
  @Query("SELECT b FROM BookReview b WHERE UPPER(b.bookTitle) LIKE UPPER(CONCAT('%', :title, '%'))")
  Page<BookReview> findByItemTitle(@Param("title") String title, Pageable pageable);

  @Override
  @Query("SELECT b FROM BookReview b WHERE UPPER(b.bookAuthor) LIKE UPPER(CONCAT('%', :creatorName, '%'))")
  Page<BookReview> findByItemCreator(@Param("creatorName") String creatorName, Pageable pageable);

  @Override
  @Query("""
          SELECT NEW com.imd.backend.domain.valueobjects.core.BaseTrendingItem(
              NEW com.imd.backend.domain.valueobjects.bookitem.BookItem(
                  b.bookId,
                  b.bookPlatform,
                  b.bookTitle,
                  b.bookArtworkUrl,
                  b.bookAuthor,
                  b.bookIsbn,
                  b.bookPageCount
              ),
              COUNT(b)
          )
          FROM BookReview b
          GROUP BY
              b.bookId,
              b.bookPlatform,
              b.bookTitle,
              b.bookArtworkUrl,
              b.bookAuthor,
              b.bookIsbn,
              b.bookPageCount
          ORDER BY COUNT(b) DESC
      """)
  List<BaseTrendingItem<BookItem>> findTrendingItems(@Param("filterType") String filterType, Pageable pageable);

  @Override
  @Query("""
          SELECT NEW com.imd.backend.domain.valueobjects.core.BaseResume(
              b.id,
              b.textContent,
              b.createdAt,
              u.id,
              u.username,
              u.email,
              p.bio,
              f.fileName,
              SIZE(b.comments),
              SIZE(b.likes),
              SIZE(p.followers),
              SIZE(p.following),
              NEW com.imd.backend.domain.valueobjects.bookitem.BookItem(
                  b.bookId,
                  b.bookPlatform,
                  b.bookTitle,
                  b.bookArtworkUrl,
                  b.bookAuthor,
                  b.bookIsbn,
                  b.bookPageCount
              )
          )
          FROM BookReview b
          JOIN b.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
      """)
  Page<BaseResume<BookItem>> findAllResumes(Pageable pageable);

  @Override
  @Query("""
    SELECT NEW com.imd.backend.domain.valueobjects.core.BaseResume(
        b.id,
        b.textContent,
        b.createdAt,
        u.id,
        u.username,
        u.email,
        p.bio,
        f.fileName,
        SIZE(b.comments),
        SIZE(b.likes),
        SIZE(p.followers),
        SIZE(p.following),
        NEW com.imd.backend.domain.valueobjects.bookitem.BookItem(
            b.bookId,
            b.bookPlatform,
            b.bookTitle,
            b.bookArtworkUrl,
            b.bookAuthor,
            b.bookIsbn,
            b.bookPageCount
        )
    )
    FROM BookReview b
    JOIN b.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
    WHERE u.id = :authorId
  """)
  Page<BaseResume<BookItem>> findResumesByAuthorId(@Param("authorId") String authorId, Pageable pageable);

  @Override
  @Query("""
    SELECT NEW com.imd.backend.domain.valueobjects.core.BaseResume(
        b.id,
        b.textContent,
        b.createdAt,
        u.id,
        u.username,
        u.email,
        p.bio,
        f.fileName,
        SIZE(b.comments),
        SIZE(b.likes),
        SIZE(p.followers),
        SIZE(p.following),
        NEW com.imd.backend.domain.valueobjects.bookitem.BookItem(
            b.bookId,
            b.bookPlatform,
            b.bookTitle,
            b.bookArtworkUrl,
            b.bookAuthor,
            b.bookIsbn,
            b.bookPageCount
        )
    )
    FROM BookReview b
    JOIN b.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
    WHERE b.id = :id
  """)
  Optional<BaseResume<BookItem>> findResumeById(@Param("id") String id);

  // ============================================================================================
  // 4. TIMELINE (Home / Global)
  // ============================================================================================

  @Override
  @Query("""
        SELECT NEW com.imd.backend.domain.valueobjects.core.BaseTimelineItem(
          b.id, b.textContent, b.createdAt,
          SIZE(b.comments), SIZE(b.likes),
          u.id, u.username, f.url,
          NEW com.imd.backend.domain.valueobjects.bookitem.BookItem(
              b.bookId,
              b.bookPlatform,
              b.bookTitle,
              b.bookArtworkUrl,
              b.bookAuthor,
              b.bookIsbn,
              b.bookPageCount
          )
        )
        FROM BookReview b
        JOIN b.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
      """)
  Page<BaseTimelineItem<BookItem>> findGlobalTimelineItems(Pageable pageable);

  @Override
  @Query("""
        SELECT NEW com.imd.backend.domain.valueobjects.core.BaseTimelineItem(
          b.id, b.textContent, b.createdAt,
          SIZE(b.comments), SIZE(b.likes),
          u.id, u.username, f.url,
          NEW com.imd.backend.domain.valueobjects.bookitem.BookItem(
              b.bookId,
              b.bookPlatform,
              b.bookTitle,
              b.bookArtworkUrl,
              b.bookAuthor,
              b.bookIsbn,
              b.bookPageCount
          )
        )
        FROM BookReview b
        JOIN b.author u
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
      Pageable pageable);
}
