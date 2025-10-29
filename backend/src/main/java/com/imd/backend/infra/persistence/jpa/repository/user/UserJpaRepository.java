package com.imd.backend.infra.persistence.jpa.repository.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.repository.UserRepository;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.UserWithProfile;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import com.imd.backend.infra.persistence.jpa.mapper.UserMapper;
import com.imd.backend.infra.persistence.jpa.projections.UserWithProfileProjection;

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
      findedUsers.getTotalElements(),
      findedUsers.getNumber(),
      findedUsers.getSize(),
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

  @Override
  public boolean existsById(String id) {
    return userJPA.existsById(id);
  }

  @Override
  public PageResult<UserWithProfile> searchUsersWithProfileByUsernamePart(String username, Pagination pageQuery) {
    final Sort.Direction direction = Sort.Direction.fromString(pageQuery.orderDirection());
    final Sort sort = Sort.by(direction, pageQuery.orderBy());
    final Pageable pageable = PageRequest.of(pageQuery.page(), pageQuery.size(), sort);

    final Page<UserWithProfileProjection> page = userJPA.searchUsersWithProfileByUsernameContaining(username, pageable);
    return new PageResult<UserWithProfile>(
      page.getContent().stream().map(p -> new UserWithProfile(
        UUID.fromString(p.getUserId()), 
        p.getUserEmail(), 
        p.getUsername(), 
        p.getProfileId(), 
        p.getBio(), 
        p.getFavoriteSong(), 
        p.getCreatedAt())).toList(),
      page.getNumberOfElements(),
      page.getTotalElements(),
      page.getNumber(),
      page.getSize(),
      page.getTotalPages()
    );
  }

  @Override
  public Optional<UserWithProfile> findUserWithProfileByUsername(String username) {
    final Optional<UserWithProfileProjection> projectionOp = userJPA.findUserWithProfileByUsername(username);

    if (projectionOp.isEmpty()) return Optional.empty();

    final var projection = projectionOp.get();
    return Optional.of(new UserWithProfile(
      UUID.fromString(
      projection.getUserId()),
      projection.getUserEmail(),
      projection.getUsername(),
      projection.getProfileId(),
      projection.getBio(),
      projection.getFavoriteSong(),
      projection.getCreatedAt()
    ));
  }
}
