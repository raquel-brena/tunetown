package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import java.util.List;
import java.util.Optional;

import com.imd.backend.api.dto.TuneetResumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;
import com.imd.backend.infra.persistence.jpa.projections.TrendingTuneProjection;

public interface TuneetJPA extends JpaRepository<TuneetEntity, String> {
    @Query("""
    SELECT new com.imd.backend.api.dto.TuneetResumoDTO(
        t.id,
        t.contentText,
        t.tunableItemArtist,
        t.tunableItemTitle,
        t.tunableItemArtworkUrl,
        t.tunableItemId,
        t.tunableItemPlataform,
        t.tunableItemType,
        t.createdAt,
        u.username,
        u.profile.id,
        u.email,
        u.id,
        COUNT(DISTINCT c.id),
        COUNT(DISTINCT l.id),
        p.bio,
        COUNT(DISTINCT f1.id),
        COUNT(DISTINCT f2.id),
        f.fileName
    )
    FROM TuneetEntity t
    LEFT JOIN t.author u
    LEFT JOIN t.comments c
    LEFT JOIN t.likes l
    LEFT JOIN u.profile p
    LEFT JOIN p.photo f
    LEFT JOIN p.following f1
    LEFT JOIN p.followers f2
    WHERE u.username = :authorId
    GROUP BY
        t.id,
        t.contentText,
        t.tunableItemArtist,
        t.tunableItemTitle,
        t.tunableItemArtworkUrl,
        t.tunableItemId,
        t.tunableItemPlataform,
        t.tunableItemType,
        t.createdAt,
        u.id,
        u.username,
        u.email,
        u.profile.id,
        p.bio,
        f.fileName
    ORDER BY t.createdAt DESC
""")
    Page<TuneetResumoDTO> findResumoByAuthorId(@Param("authorId") String authorId, Pageable pageable);

    @Query("""
    SELECT t.id,
        t.contentText,
        t.tunableItemArtist,
        t.tunableItemTitle,
        t.tunableItemArtworkUrl,
        t.tunableItemId,
        t.tunableItemPlataform,
        t.tunableItemType,
        t.createdAt,
        u.username,
        u.profile.id,
        u.email,
        u.id,
        COUNT(DISTINCT c.id),
        COUNT(DISTINCT l.id)
    FROM TuneetEntity t
    LEFT JOIN t.author u
    LEFT JOIN t.comments c
    LEFT JOIN t.likes l
    WHERE t.id = :id
    GROUP BY t.id, t.contentText, t.tunableItemArtist, t.tunableItemArtworkUrl,
             t.tunableItemId, t.tunableItemPlataform, t.tunableItemType, t.createdAt,  u.id,
        u.username,
        u.email,
        u.profile.id
""")
    Optional<TuneetResumoDTO> findResumoById(@Param("id") String id);


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
}
