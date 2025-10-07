package com.imd.backend.domain.repository;

import java.util.Optional;

import com.imd.backend.domain.entities.PageResult;
import com.imd.backend.domain.entities.Pagination;
import com.imd.backend.domain.entities.User;

public interface UserRepository {
  public void create(User user);
  public PageResult<User> findAll(Pagination pageQuery);
  public Optional<User> findByUsername(String username);
  public boolean existsByEmail(String email);
  public boolean existsByUsername(String username);
}
