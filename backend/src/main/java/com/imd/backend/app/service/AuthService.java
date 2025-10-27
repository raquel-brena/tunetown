package com.imd.backend.app.service;

import com.imd.backend.api.dto.auth.LoginResponse;
import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.infra.security.TuneUserDetails;
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

    public LoginResponse login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        TuneUserDetails tuneUserDetails = tuneUserDetailsService.loadUserByUsername(username);

        User user = this.userService.findUserByUsername(tuneUserDetails.getUsername());

        String accessToken = jwtService.generateAccessToken(tuneUserDetails);
        UserDTO userReponse = new UserDTO(user.getId().toString(), user.getUsername(), user.getEmail(), user.getProfileId());

        return new LoginResponse(accessToken, userReponse);
    }

    public User register(String username, String email, String password) throws BusinessException {
        final var encryptedPassword = passwordEncoder.encode(password);
        final User userToCreate = User.createNew(email, username, encryptedPassword, null);
        return userService.createUser(userToCreate);
    }
}
