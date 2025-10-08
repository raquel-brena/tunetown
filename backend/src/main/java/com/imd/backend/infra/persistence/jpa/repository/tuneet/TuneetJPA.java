package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;
import com.imd.backend.infra.persistence.jpa.projections.TrendingTuneProjection;

public interface TuneetJPA extends JpaRepository<TuneetEntity, String> {
  Page<TuneetEntity> findByAuthorId(String authorId, Pageable pageable);  

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
    WHERE t.tunableItemType = :itemType
    GROUP BY t.tunableItemId, t.tunableItemTitle, t.tunableItemArtist,
      t.tunableItemPlataform, t.tunableItemType, t.tunableItemArtworkUrl
    ORDER BY COUNT(t) DESC
  """)
  List<TrendingTuneProjection> findTrendingTunesByType(
          @Param("itemType") String itemType,
          Pageable pageable
  ); 
}
