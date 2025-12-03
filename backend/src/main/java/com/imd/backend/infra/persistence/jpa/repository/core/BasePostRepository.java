package com.imd.backend.infra.persistence.jpa.repository.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;
import com.imd.backend.domain.valueObjects.core.PostItem;

@NoRepositoryBean
public interface BasePostRepository<T extends BasePost, I extends PostItem > extends JpaRepository<T, String> {
  Page<T> findByAuthorId(String authorId, Pageable pageable);
  List<BaseTrendingItem<I>> findTrendingItems(@Param("filterType") String filterType, Pageable pageable);
}
