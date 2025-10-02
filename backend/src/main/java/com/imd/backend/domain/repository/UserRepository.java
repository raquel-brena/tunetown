package com.imd.backend.domain.repository;

import java.util.List;
import java.util.Optional;

import com.imd.backend.domain.entities.User;

public interface UserRepository {
  public void create(User user);
  public List<User> findAll();
  public Optional<User> findByUsername(String username);
  public boolean existsByEmail(String email);
  public boolean existsByUsername(String username);
}
