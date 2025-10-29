package com.imd.backend.api.controller;

import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.app.service.UserService;
import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.UserWithProfile;
import com.imd.backend.infra.persistence.jpa.mapper.UserMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResult<UserDTO>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "id") String orderBy,
        @RequestParam(defaultValue = "ASC") String orderDirection
    ) {
        final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);

        final PageResult<User> profiles = service.findAllUsers(pagination);
        
        final PageResult<UserDTO> dtoPage = new PageResult<UserDTO>(
            profiles.itens().stream().map(UserMapper::toDTO).toList(),
            profiles.pageItens(),
            profiles.totalItens(),
            profiles.currentPage(),
            profiles.pageSize(),
            profiles.totalPages()
        );
        
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findUserByUsername(String username) {
        final User user = service.findUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/with-profile/username/{username}")
    public ResponseEntity<UserWithProfile> findUserWithProfileByUsername(
        @PathVariable(required = true) String username
    ) {
        final UserWithProfile user = this.service.findUserWithProfileByUsername(username);
        
        return ResponseEntity.ok(user);
    }    

    @GetMapping("/with-profile/search-by-username-part/{username}")
    public ResponseEntity<PageResult<UserWithProfile>> searchUserWithProfileByUsernamePart(
        @PathVariable(required = true) String username,     
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "id") String orderBy,
        @RequestParam(defaultValue = "ASC") String orderDirection
    ) {
        final Pagination pagination = new Pagination(page, size, orderBy, orderDirection);

        final PageResult<UserWithProfile> users = service.searchUsersWithProfileByUsernamePart(username, pagination);

        return ResponseEntity.ok(users);
    }    
}
