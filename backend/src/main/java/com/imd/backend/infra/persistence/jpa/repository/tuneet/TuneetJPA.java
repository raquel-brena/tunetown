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
    SELECT t.id,
        t.contentText,
        t.tunableItemArtist,
        t.tunableItemTitle,
        t.tunableItemArtworkUrl,
        t.tunableItemId,
        t.tunableItemPlataform,
        t.tunableItemType,
        t.createdAt,
        t.authorId,
        (SELECT u.username FROM UserEntity u WHERE u.id = t.authorId),
        COUNT(DISTINCT c.id),
        COUNT(DISTINCT l.id)
    FROM TuneetEntity t
    LEFT JOIN t.comments c
    LEFT JOIN t.likes l
    WHERE t.authorId = :authorId
    GROUP BY t.id, t.contentText, t.tunableItemArtist, t.tunableItemArtworkUrl,
             t.tunableItemId, t.tunableItemPlataform, t.tunableItemType, t.createdAt
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
        t.authorId,
        (SELECT u.username FROM UserEntity u WHERE u.id = t.author.username),
        COUNT(DISTINCT c.id),
        COUNT(DISTINCT l.id)
    FROM TuneetEntity t
    LEFT JOIN t.comments c
    LEFT JOIN t.likes l
    WHERE t.id = :id
    GROUP BY t.id, t.contentText, t.tunableItemArtist, t.tunableItemArtworkUrl,
             t.tunableItemId, t.tunableItemPlataform, t.tunableItemType, t.createdAt
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
       t.createdAt as createdAt,
      COUNT(t) as tuneetCount
    FROM TuneetEntity t
    GROUP BY t.tunableItemId, t.tunableItemTitle, t.tunableItemArtist,
      t.tunableItemPlataform, t.tunableItemType, t.tunableItemArtworkUrl, t.createdAt
    ORDER BY COUNT(t) DESC
  """)
  List<TrendingTuneProjection> findTrendingTunesByType(
          @Param("itemType") String itemType,
          Pageable pageable
  ); 
}
