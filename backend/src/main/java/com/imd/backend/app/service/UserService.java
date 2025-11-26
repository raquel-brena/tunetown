package com.imd.backend.app.service;

import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.valueObjects.UserWithProfile;
import com.imd.backend.infra.persistence.jpa.projections.UserWithProfileProjection;
import com.imd.backend.infra.persistence.jpa.repository.user.UserRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public User createUser(User user) throws BusinessException {
        if ("tuto".equalsIgnoreCase(user.getUsername())) {
            throw new BusinessException("O username 'tuto' é reservado e não pode ser usado");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Username já registrado");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email já registrado");
        }

        userRepository.save(user);
        return user;
    }

    public User findUserByUsername (String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new BusinessException("User not found");
        }
        return user.get();
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable); 
    }

    public boolean userExistsById(UUID id) {
        return this.userRepository.existsById(id.toString());
    }

    public UserWithProfile findUserWithProfileByUsername(String username) {
        final var userOp = this.userRepository.findUserWithProfileByUsername(username);

        if(userOp.isEmpty())
            throw new NotFoundException("Usuário não encontrado!");

        final UserWithProfileProjection projection = userOp.get();
        final UserWithProfile user = new UserWithProfile(
            UUID.fromString(projection.getUserId()),
            projection.getUserEmail(),
            projection.getUsername(),
            projection.getProfileId(),
            projection.getBio(),
            projection.getFavoriteSong(),
            projection.getCreatedAt(),
            projection.getFileName()
        );

        if(user.getPhotoFileName() != null) {
            final String presignedUrl = FileService.applyPresignedUrl(user.getPhotoFileName());
            user.setPhotoUrl(presignedUrl);
        }

        return user;
    }

    public Page<UserWithProfile> searchUsersWithProfileByUsernamePart(String usernamePart, Pageable pageable) {
        final Page<UserWithProfileProjection> projection = this.userRepository.searchUsersWithProfileByUsernameContaining(usernamePart, pageable);
        final Page<UserWithProfile> users = projection
            .map(p -> new UserWithProfile(UUID.fromString(p.getUserId()), p.getUserEmail(), p.getUsername(),
             p.getProfileId(), p.getBio(), p.getFavoriteSong(), p.getCreatedAt(), p.getFileName())); 

        return users;
    }
}
