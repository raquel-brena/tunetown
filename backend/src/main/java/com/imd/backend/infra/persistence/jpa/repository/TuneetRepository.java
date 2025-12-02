package com.imd.backend.infra.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imd.backend.infra.persistence.jpa.projections.TimelineItemProjection;
import com.imd.backend.infra.persistence.jpa.projections.TrendingTuneProjection;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;

@Repository("TuneetJpaRepository")
public interface TuneetRepository extends BasePostRepository<Tuneet> {
    @Query("""
        SELECT
          t.id as tuneetId,
          t.textContent as contentText,
          t.tunableItemArtist as tunableItemArtist,
          t.tunableItemTitle as tunableItemTitle,
          t.tunableItemArtworkUrl as tunableItemArtworkUrl,
          t.tunableItemId as tunableItemId,
          t.tunableItemPlataform as tunableItemPlataform,
          t.tunableItemType as tunableItemType,
          t.createdAt as createdAt,
          u.username as username,
          u.profile.id as profileId,
          u.email as email,
          u.id as authorId,
          p.bio as bio,
          f.fileName as fileNamePhoto,
          SIZE(t.comments) as totalComments,
          SIZE(t.likes) as totalLikes,
          SIZE(p.followers) as totalFollowers,
          SIZE(p.following) as totalFollowing
        FROM Tuneet t
        LEFT JOIN t.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
        WHERE u.id = :authorId
    """)
    Page<TuneetResumeProjection> findTuneetResumeByAuthorId(@Param("authorId") String authorId, Pageable pageable);

    @Query("""
          SELECT
            t.id as tuneetId,
            t.textContent as contentText,
            t.tunableItemArtist as tunableItemArtist,
            t.tunableItemTitle as tunableItemTitle,
            t.tunableItemArtworkUrl as tunableItemArtworkUrl,
            t.tunableItemId as tunableItemId,
            t.tunableItemPlataform as tunableItemPlataform,
            t.tunableItemType as tunableItemType,
            t.createdAt as createdAt,
            u.username as username,
            u.profile.id as profileId,
            u.email as email,
            u.id as authorId,
            p.bio as bio,          
            f.fileName as fileNamePhoto,
            SIZE(t.comments) as totalComments,
            SIZE(t.likes) as totalLikes,
            SIZE(p.followers) as totalFollowers,
            SIZE(p.following) as totalFollowing
          FROM Tuneet t
          LEFT JOIN t.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE t.id = :id
        """)
    Optional<TuneetResumeProjection> findTuneetResumeById(@Param("id") String id);

    @Query("""
        SELECT
          t.id as tuneetId,
          t.textContent as contentText,
          t.tunableItemArtist as tunableItemArtist,
          t.tunableItemTitle as tunableItemTitle,
          t.tunableItemArtworkUrl as tunableItemArtworkUrl,
          t.tunableItemId as tunableItemId,
          t.tunableItemPlataform as tunableItemPlataform,
          t.tunableItemType as tunableItemType,
          t.createdAt as createdAt,
          u.username as username,
          u.profile.id as profileId,
          u.email as email,
          u.id as authorId,
          p.bio as bio,          
          f.fileName as fileNamePhoto,
          SIZE(t.comments) as totalComments,
          SIZE(t.likes) as totalLikes,
          SIZE(p.followers) as totalFollowers,
          SIZE(p.following) as totalFollowing
        FROM Tuneet t
        LEFT JOIN t.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
  """)
    Page<TuneetResumeProjection> findAllTuneetResume(Pageable pageable);

    Page<Tuneet> findByTunableItemTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Tuneet> findByTunableItemArtistContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
    SELECT t.tunableItemId as itemId,
      t.tunableItemTitle as title,
      t.tunableItemArtist as artist,
      t.tunableItemPlataform as platformId,
      t.tunableItemType as itemType,
      t.tunableItemArtworkUrl as artworkUrl,
      COUNT(t) as tuneetCount
    FROM Tuneet t
    GROUP BY t.tunableItemId, t.tunableItemTitle, t.tunableItemArtist,
      t.tunableItemPlataform, t.tunableItemType, t.tunableItemArtworkUrl
    ORDER BY COUNT(t) DESC
  """)
    List<TrendingTuneProjection> findTrendingTunesTunableItemType(
            @Param("itemType") String itemType,
            Pageable pageable
    );

    Page<Tuneet> findByAuthorId(String authorId, Pageable pageable);

    @Query("""
    SELECT
      t.id as tuneetId,
      t.textContent as textContent,
      t.createdAt as createdAt,
      t.tunableItemTitle as tunableItemTitle,
      t.tunableItemArtist as tunableItemArtist,
      t.tunableItemArtworkUrl as tunableItemArtworkUrl,
      t.tunableItemType as tunableItemType,
      u.id as authorId,
      u.username as authorUsername,
      f.url as authorAvatarUrl,
      f.fileName as authorAvatarFileName,
      SIZE(t.comments) as totalComments,
      SIZE(t.likes) as totalLikes
    FROM Tuneet t
    JOIN t.author u
    LEFT JOIN u.profile p ON u.id = p.user.id
    LEFT JOIN p.photo f
  """)
    Page<TimelineItemProjection> findGlobalTimeline(Pageable pageable);

    @Query("""
    SELECT
      t.id as tuneetId,
      t.textContent as textContent,
      t.createdAt as createdAt,
      t.tunableItemTitle as tunableItemTitle,
      t.tunableItemArtist as tunableItemArtist,
      t.tunableItemArtworkUrl as tunableItemArtworkUrl,
      t.tunableItemType as tunableItemType,
      u.id as authorId,
      u.username as authorUsername,
      f.url as authorAvatarUrl,
      f.fileName as authorAvatarFileName,
      SIZE(t.comments) as totalComments,
      SIZE(t.likes) as totalLikes
    FROM Tuneet t
    JOIN t.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
    WHERE p IN (
      SELECT fol.followed
      FROM Profile p_current
      JOIN p_current.following fol
      WHERE p_current.user.id = :currentUserId
    )
    OR u.id = :currentUserId
  """)
    Page<TimelineItemProjection> findHomeTimeline(
            @Param("currentUserId") String currentUserId,
            Pageable pageable
    );


    public Page<Tuneet> findAll(Pageable pagination);

    public Page<Tuneet> findByTunableItemId(String tunableItemId, Pageable pagination);

    public Page<Tuneet> findByTunableItemTitleContaining(String word, Pageable pagination);

    public Page<Tuneet> findByTunableItemArtistContaining(String word, Pageable pagination);

    @Query("""
    SELECT 
      t.tunableItemId AS itemId,
      t.tunableItemTitle AS title,
      t.tunableItemArtist AS artist,
      t.tunableItemPlataform AS platformId,
      t.tunableItemType AS itemType,
      t.tunableItemArtworkUrl AS artworkUrl,
      COUNT(t.id) AS tuneetCount
    FROM Tuneet t
    WHERE t.tunableItemType = :type
    GROUP BY 
      t.tunableItemId, 
      t.tunableItemTitle, 
      t.tunableItemArtist,
      t.tunableItemPlataform,
      t.tunableItemType, 
      t.tunableItemArtworkUrl
    ORDER BY COUNT(t.id) DESC
""")
    Page<TrendingTuneProjection> findTrendingTunesByTunableItemType(
            @Param("type") String type,
            Pageable pageable
    );

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
