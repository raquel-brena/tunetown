package com.imd.backend.domain.repository;

import java.util.Optional;

import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.UserWithProfile;

public interface UserRepository {
  public void create(User user);
  public PageResult<User> findAll(Pagination pageQuery);
  public Optional<User> findByUsername(String username);
  public boolean existsByEmail(String email);
  public boolean existsByUsername(String username);
  public boolean existsById(String id);
  public Optional<UserWithProfile> findUserWithProfileByUsername(String username);
  public PageResult<UserWithProfile> searchUsersWithProfileByUsernamePart(String username,Pagination pageQuery);
}
