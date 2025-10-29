package com.imd.backend.app.service;

import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.repository.UserRepository;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.UserWithProfile;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
        @Qualifier("UserJpaRepository") UserRepository userRepository
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

        userRepository.create(user);
        return user;
    }

    public User findUserByUsername (String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new BusinessException("User not found");
        }
        return user.get();
    }

    public PageResult<User> findAllUsers(Pagination pageable) {
        return userRepository.findAll(pageable); 
    }

    public boolean userExistsById(String id) {
        return this.userRepository.existsById(id);
    }

    public UserWithProfile findUserWithProfileByUsername(String username) {
        final var userOp = this.userRepository.findUserWithProfileByUsername(username);

        if(userOp.isEmpty())
            throw new NotFoundException("Usuário não encontrado!");

        final UserWithProfile user = userOp.get();

        if(user.getPhotoFileName() != null) {
            final String presignedUrl = FileService.applyPresignedUrl(user.getPhotoFileName());
            user.setPhotoUrl(presignedUrl);
        }

        return user;
    }

    public PageResult<UserWithProfile> searchUsersWithProfileByUsernamePart(String usernamePart, Pagination pageable) {
        final PageResult<UserWithProfile> users = this.userRepository.searchUsersWithProfileByUsernamePart(usernamePart, pageable);
        users.itens().forEach(u -> {
            if(u.getPhotoFileName() == null || u.getPhotoFileName().isBlank()) return;

            final String presignedUrl = FileService.applyPresignedUrl(u.getPhotoFileName());
            u.setPhotoUrl(presignedUrl);
        });

        return users;
    }
}
