package com.imd.backend.app.service;

import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.infra.persistence.jpa.entity.User;
import com.imd.backend.infra.persistence.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws BusinessException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Username já registrado");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email já registrado");
        }

        return userRepository.save(user);
    }
}
