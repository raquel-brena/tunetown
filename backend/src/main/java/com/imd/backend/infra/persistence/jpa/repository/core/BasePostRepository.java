package com.imd.backend.infra.persistence.jpa.repository.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;
import com.imd.backend.domain.valueObjects.core.PostItem;

@NoRepositoryBean
public interface BasePostRepository<T extends BasePost, I extends PostItem > extends JpaRepository<T, String> {
  Page<T> findByItemId(@Param("itemId") String itemId, Pageable pageable);

  Page<T> findByItemTitle(@Param("title") String title, Pageable pageable);

  Page<T> findByItemCreator(@Param("creatorName") String creatorName, Pageable pageable);
  
  Page<T> findByAuthorId(String authorId, Pageable pageable);
  
  List<BaseTrendingItem<I>> findTrendingItems(@Param("filterType") String filterType, Pageable pageable);

  Page<BaseResume<I>> findAllResumes(Pageable pageable);

  Page<BaseResume<I>> findResumesByAuthorId(String authorId, Pageable pageable);

  Optional<BaseResume<I>> findResumeById(@Param("id") String id);

  Page<BaseTimelineItem<I>> findGlobalTimelineItems(Pageable pageable);

  Page<BaseTimelineItem<I>> findHomeTimelineItems(
    @Param("currentUserId") String currentUserId, 
    Pageable pageable
  );  
}
