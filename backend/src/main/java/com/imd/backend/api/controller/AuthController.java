package com.imd.backend.api.controller;

import com.imd.backend.api.dto.auth.LoginRequest;
import com.imd.backend.api.dto.auth.LoginResponse;
import com.imd.backend.api.dto.auth.RegisterRequest;
import com.imd.backend.api.dto.auth.RegisterResponse;
import com.imd.backend.app.service.AuthService;
import com.imd.backend.domain.exception.BadRequestException;
import com.imd.backend.domain.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Controller
@RequestMapping("api/auth")
public class AuthController {

    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var accessToken = authService.login(request.login(), request.password());
        return ResponseEntity.ok(new LoginResponse(accessToken));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            var user = authService.register(request.username(), request.email(), request.password());
            var location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
            return ResponseEntity.created(location).body(RegisterResponse.fromUser(user));
        } catch (BusinessException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
