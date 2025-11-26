package com.imd.backend.infra.persistence.jpa.repository.user;


import com.imd.backend.domain.entities.core.User;
import com.imd.backend.infra.persistence.jpa.projections.UserWithProfileProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(
        "SELECT " +
        "  u.id as userId, " +
        "  u.email as userEmail, " +
        "  u.username as username, " +
        "  p.id as profileId, " +
        "  p.bio as bio, " +
        "  p.favoriteSong as favoriteSong, " +
        "  p.createdAt as createdAt, " +
        "  ph.fileName as fileName " +
        "FROM User u " +
        // O JOIN funciona por causa da relação estabelecida na entity
        "JOIN u.profile p " +
        "LEFT JOIN p.photo ph " + 
        "WHERE u.username = :username"
    )
    public Optional<UserWithProfileProjection> findUserWithProfileByUsername(@Param("username") String username);  

    @Query("SELECT " +
        "  u.id as userId, " +
        "  u.email as userEmail, " +
        "  u.username as username, " +
        "  p.id as profileId, " +
        "  p.bio as bio, " +
        "  p.favoriteSong as favoriteSong, " +
        "  p.createdAt as createdAt, " +
        "  ph.fileName as fileName " +
        "FROM User u " +
        // O JOIN funciona por causa da relação estabelecida na entity
        "JOIN u.profile p " +
        "LEFT JOIN p.photo ph " +
        "WHERE UPPER(u.username) LIKE UPPER(CONCAT('%', :username, '%'))"
    )
    public Page<UserWithProfileProjection> searchUsersWithProfileByUsernameContaining(@Param("username") String username, Pageable pageable);    
}
