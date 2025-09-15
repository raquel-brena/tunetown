package com.imd.backend.app.service;

import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.infra.persistence.jpa.entity.User;
import com.imd.backend.infra.security.TuneUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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

    public User register(String username, String email, String password) throws BusinessException {
        var encryptedPassword = passwordEncoder.encode(password);
        return userService.createUser(new User(email, username, encryptedPassword));
    }
}
