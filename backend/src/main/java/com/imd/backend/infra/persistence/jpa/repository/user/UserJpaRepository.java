package com.imd.backend.infra.persistence.jpa.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.repository.UserRepository;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import com.imd.backend.infra.persistence.jpa.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository("UserJpaRepository")
@RequiredArgsConstructor
public class UserJpaRepository implements UserRepository{
  private final UserJPA userJPA;

  @Override
  public void create(User user) {
    final UserEntity entityToSave = UserMapper.domainToEntity(user);

    userJPA.save(entityToSave);
  }

  @Override
  public List<User> findAll() {
    final List<UserEntity> findedUsers = this.userJPA.findAll();
    
    return findedUsers.stream()
      .map(entity -> UserMapper.entityToDomain(entity))
      .toList();
  }

  @Override
  public Optional<User> findByUsername(String username) {
    final Optional<UserEntity> findedUser =  userJPA.findByUsername(username);

    if(findedUser.isEmpty()) return Optional.empty();

    return Optional.of(UserMapper.entityToDomain(findedUser.get()));      
  }

  @Override
  public boolean existsByEmail(String email) {
    return userJPA.existsByEmail(email);
  }

  @Override
  public boolean existsByUsername(String username) {
    return userJPA.existsByUsername(username);
  }
}
