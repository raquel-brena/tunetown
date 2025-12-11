package com.imd.backend.infra.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.*;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;

@Repository("TuneetJpaRepository")
public interface TuneetRepository extends BasePostRepository<Tuneet, TunableItem> {
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
              NEW com.imd.backend.domain.valueObjects.TunableItem.TunableItem(
                  t.tunableItemId,
                  t.tunableItemPlataform,
                  t.tunableItemTitle,
                  t.tunableItemArtist,
                  t.tunableItemArtworkUrl,
                  t.tunableItemType
              )
          )
          FROM Tuneet t
          JOIN t.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE u.id = :authorId
      """)
    Page<BaseResume<TunableItem>> findResumesByAuthorId(@Param("authorId") String authorId, Pageable pageable);

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
              NEW com.imd.backend.domain.valueObjects.TunableItem.TunableItem(
                  t.tunableItemId,
                  t.tunableItemPlataform,
                  t.tunableItemTitle,
                  t.tunableItemArtist,
                  t.tunableItemArtworkUrl,
                  t.tunableItemType
              )
          )
          FROM Tuneet t
          JOIN t.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE t.id = :id
      """)
    Optional<BaseResume<TunableItem>> findResumeById(@Param("id") String id);

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
          NEW com.imd.backend.domain.valueObjects.TunableItem.TunableItem(
              t.tunableItemId,
              t.tunableItemPlataform,
              t.tunableItemTitle,
              t.tunableItemArtist,
              t.tunableItemArtworkUrl,
              t.tunableItemType
          )
      )
        FROM Tuneet t
        JOIN t.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
    """)
    Page<BaseResume<TunableItem>> findAllResumes(Pageable pageable);

    Page<Tuneet> findByTunableItemTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Tuneet> findByTunableItemArtistContainingIgnoreCase(String name, Pageable pageable);

    @Override
    @Query("""
        SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTrendingItem(
            NEW com.imd.backend.domain.valueObjects.TunableItem.TunableItem(
                t.tunableItemId,
                t.tunableItemPlataform,
                t.tunableItemTitle,
                t.tunableItemArtist,
                t.tunableItemArtworkUrl,
                t.tunableItemType       
            ),
            COUNT(t)
        )
        FROM Tuneet t
        WHERE UPPER(t.tunableItemType) = UPPER(:filterType)
        GROUP BY 
            t.tunableItemId, 
            t.tunableItemPlataform, 
            t.tunableItemTitle, 
            t.tunableItemArtist, 
            t.tunableItemArtworkUrl, 
            t.tunableItemType
        ORDER BY COUNT(t) DESC
    """)
    List<BaseTrendingItem<TunableItem>> findTrendingItems(
        @Param("filterType") String filterType, 
        Pageable pageable
    );

    Page<Tuneet> findByAuthorId(String authorId, Pageable pageable);

    @Query("""
      SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTimelineItem(
        t.id, t.textContent, t.createdAt,
        SIZE(t.comments), SIZE(t.likes),
        u.id, u.username, f.url, 
        NEW com.imd.backend.domain.valueObjects.TunableItem.TunableItem(
          t.tunableItemId, t.tunableItemPlataform, t.tunableItemTitle,
          t.tunableItemArtist, t.tunableItemArtworkUrl, t.tunableItemType
        )
      )
      FROM Tuneet t
      JOIN t.author u
      LEFT JOIN u.profile p
      LEFT JOIN p.photo f
    """)
    Page<BaseTimelineItem<TunableItem>> findGlobalTimelineItems(Pageable pageable);

    @Query("""
      SELECT NEW com.imd.backend.domain.valueObjects.core.BaseTimelineItem(
        t.id, t.textContent, t.createdAt,
        SIZE(t.comments), SIZE(t.likes),
        u.id, u.username, f.url,
        NEW com.imd.backend.domain.valueObjects.TunableItem.TunableItem(
          t.tunableItemId, t.tunableItemPlataform, t.tunableItemTitle,
          t.tunableItemArtist, t.tunableItemArtworkUrl, t.tunableItemType
        )
      )
      FROM Tuneet t
      JOIN t.author u
      LEFT JOIN u.profile p
      LEFT JOIN p.photo f
      WHERE p IN (
        SELECT fol.followed FROM Profile p_curr JOIN p_curr.following fol
        WHERE p_curr.user.id = :currentUserId
      )
      OR u.id = :currentUserId
    """)    
    Page<BaseTimelineItem<TunableItem>> findHomeTimelineItems(
      @Param("currentUserId") String currentUserId,
      Pageable pageable
    );

    public Page<Tuneet> findAll(Pageable pagination);

    @Override
    @Query("SELECT t FROM Tuneet t WHERE t.tunableItemId = :itemId")
    Page<Tuneet> findByItemId(@Param("itemId") String itemId, Pageable pageable);

    @Override
    @Query("SELECT t FROM Tuneet t WHERE UPPER(t.tunableItemTitle) LIKE UPPER(CONCAT('%', :title, '%'))")
    Page<Tuneet> findByItemTitle(@Param("title") String title, Pageable pageable);

    @Override
    @Query("SELECT t FROM Tuneet t WHERE UPPER(t.tunableItemArtist) LIKE UPPER(CONCAT('%', :creatorName, '%'))")
    Page<Tuneet> findByItemCreator(@Param("creatorName") String creatorName, Pageable pageable);

    @Query("""
    SELECT TimeLineItem(
        t.id,
        t.textContent,
        t.createdAt,
        t.author.id,
        t.author.username,
        t.tunableItemTitle,
        t.tunableItemArtist,
        t.tunableItemArtworkUrl
    )
    FROM Tuneet t
    ORDER BY t.createdAt DESC
""")
    public Page<TimeLineItem> getGlobalTimeline(Pageable pagination);

    @Query("""
    SELECT t
    FROM Tuneet t
    WHERE t.author.id = :authorId
    ORDER BY t.createdAt DESC
""")
    public Page<TimeLineItem> getHomeTimeline(String currentUserId, Pageable pagination);
}
