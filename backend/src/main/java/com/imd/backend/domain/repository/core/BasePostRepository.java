package com.imd.backend.domain.repository.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasePostRepository<T> extends JpaRepository<T, String> {
  Page<T> findByAuthorId(String authorId, Pageable pageable);
}
