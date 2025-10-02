package com.imd.backend.app.service;

import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import com.imd.backend.infra.security.TuneUserDetailsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.imd.backend.infra.security.JwtService;

@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TuneUserDetailsService tuneUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, @Lazy AuthenticationManager authenticationManager, JwtService jwtService, TuneUserDetailsService tuneUserDetailsService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tuneUserDetailsService = tuneUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var tuneUserDetails = tuneUserDetailsService.loadUserByUsername(username);
        return jwtService.generateAccessToken(tuneUserDetails);
    }

    public UserEntity register(String username, String email, String password) throws BusinessException {
        final var encryptedPassword = passwordEncoder.encode(password);
        final User userToCreate = User.createNew(email, username, encryptedPassword, null);
        return userService.createUser(userToCreate);
    }
}
