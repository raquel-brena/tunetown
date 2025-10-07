package com.imd.backend.infra.persistence.jpa.repository.user;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.imd.backend.domain.entities.PageResult;
import com.imd.backend.domain.entities.Pagination;
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
  public PageResult<User> findAll(Pagination pageQuery) {
    final Sort.Direction direction = Sort.Direction.fromString(pageQuery.orderDirection());
    final Sort sort = Sort.by(direction, pageQuery.orderBy());
    final Pageable pageable = PageRequest.of(pageQuery.page(), pageQuery.size(), sort); 

    final var findedUsers = this.userJPA.findAll(pageable);
    
    return new PageResult<User>(
      findedUsers.getContent().stream().map(entity -> UserMapper.entityToDomain(entity)).toList(), 
      findedUsers.getNumberOfElements(),
      findedUsers.getSize(),
      findedUsers.getTotalElements(),
      findedUsers.getTotalPages()
    );
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
