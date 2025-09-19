package com.imd.backend.app.service;

import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import com.imd.backend.infra.persistence.jpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CrudService<String, UserEntity> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) throws BusinessException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BusinessException("Username já registrado");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email já registrado");
        }

        return userRepository.save(user);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserEntity findById(String s) {
        return null;
    }

    @Override
    public UserEntity create(UserEntity user) {
        return null;
    }

    @Override
    public UserEntity update(UserEntity user) {
        return null;
    }

    @Override
    public void delete(String s) {

    }
}
