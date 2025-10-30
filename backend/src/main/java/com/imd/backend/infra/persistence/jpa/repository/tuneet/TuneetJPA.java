package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;
import com.imd.backend.infra.persistence.jpa.projections.TimelineItemProjection;
import com.imd.backend.infra.persistence.jpa.projections.TrendingTuneProjection;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;

public interface TuneetJPA extends JpaRepository<TuneetEntity, String> {
  @Query("""
        SELECT
          t.id as tuneetId,
          t.contentText as contentText,
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
        FROM TuneetEntity t
        LEFT JOIN t.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
        WHERE u.id = :authorId
    """)
    Page<TuneetResumeProjection> findTuneetResumeByAuthorId(@Param("authorId") String authorId, Pageable pageable);

    @Query("""
          SELECT
            t.id as tuneetId,
            t.contentText as contentText,
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
          FROM TuneetEntity t
          LEFT JOIN t.author u
          LEFT JOIN u.profile p
          LEFT JOIN p.photo f
          WHERE t.id = :id
        """)
  Optional<TuneetResumeProjection> findTuneetResumeById(@Param("id") String id);

  @Query("""
        SELECT
          t.id as tuneetId,
          t.contentText as contentText,
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
        FROM TuneetEntity t
        LEFT JOIN t.author u
        LEFT JOIN u.profile p
        LEFT JOIN p.photo f
  """)
  Page<TuneetResumeProjection> findAllTuneetResume(Pageable pageable);

  Page<TuneetEntity> findByTunableItemId(String tunableItemId, Pageable pageable);

  Page<TuneetEntity> findByTunableItemTitleContainingIgnoreCase(String title, Pageable pageable); 
  
  Page<TuneetEntity> findByTunableItemArtistContainingIgnoreCase(String name, Pageable pageable);

  @Query("""
    SELECT t.tunableItemId as itemId,
      t.tunableItemTitle as title,
      t.tunableItemArtist as artist,
      t.tunableItemPlataform as platformId,
      t.tunableItemType as itemType,
      t.tunableItemArtworkUrl as artworkUrl,
      COUNT(t) as tuneetCount
    FROM TuneetEntity t
    GROUP BY t.tunableItemId, t.tunableItemTitle, t.tunableItemArtist,
      t.tunableItemPlataform, t.tunableItemType, t.tunableItemArtworkUrl
    ORDER BY COUNT(t) DESC
  """)
  List<TrendingTuneProjection> findTrendingTunesByType(
          @Param("itemType") String itemType,
          Pageable pageable
  ); 

  Page<TuneetEntity> findByAuthorId(String authorId, Pageable pageable);

  @Query("""
    SELECT
      t.id as tuneetId,
      t.contentText as textContent,
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
    FROM TuneetEntity t
    JOIN t.author u
    LEFT JOIN u.profile p ON u.id = p.user.id
    LEFT JOIN p.photo f
  """)
  Page<TimelineItemProjection> findGlobalTimeline(Pageable pageable);  

  @Query("""
    SELECT
      t.id as tuneetId,
      t.contentText as textContent,
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
    FROM TuneetEntity t
    JOIN t.author u
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
    WHERE p IN (
      SELECT fol.followed
      FROM ProfileEntity p_current
      JOIN p_current.following fol
      WHERE p_current.user.id = :currentUserId
    )
    OR u.id = :currentUserId
  """)
  Page<TimelineItemProjection> findHomeTimeline(
    @Param("currentUserId") String currentUserId,
    Pageable pageable
  );  
}
