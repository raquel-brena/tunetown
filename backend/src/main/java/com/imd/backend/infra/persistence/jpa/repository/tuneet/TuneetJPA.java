package com.imd.backend.infra.persistence.jpa.repository.tuneet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

public interface TuneetJPA extends JpaRepository<TuneetEntity, String> {
  Page<TuneetEntity> findByAuthorId(String authorId, Pageable pageable);  

  Page<TuneetEntity> findByTunableItemId(String tunableItemId, Pageable pageable);

  Page<TuneetEntity> findByTunableItemTitleContainingIgnoreCase(String title, Pageable pageable); 
  
  Page<TuneetEntity> findByTunableItemArtistContainingIgnoreCase(String name, Pageable pageable);
}
