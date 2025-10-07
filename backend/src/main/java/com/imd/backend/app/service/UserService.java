package com.imd.backend.app.service;

import com.imd.backend.domain.entities.PageResult;
import com.imd.backend.domain.entities.Pagination;
import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.repository.UserRepository;

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
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Username já registrado");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email já registrado");
        }

        userRepository.create(user);
        return user;
    }

    public PageResult<User> findAllUsers(Pagination pageable) {
        return userRepository.findAll(pageable); 
    }
}
