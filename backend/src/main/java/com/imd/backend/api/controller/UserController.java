package com.imd.backend.api.controller;

import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.app.service.UserService;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import com.imd.backend.infra.persistence.jpa.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserEntity> profiles = service.findAll(pageable);
        Page<UserDTO> dtoPage = profiles.map(UserMapper::toDTO);
        if (!dtoPage.hasContent()) {
            throw new NotFoundException("Nenhum profile encontrado.");
        }
        return ResponseEntity.ok(dtoPage);
    }
}
