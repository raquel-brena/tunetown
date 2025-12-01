package com.imd.backend.app.service;

import com.imd.backend.api.dto.auth.LoginResponse;
import com.imd.backend.api.dto.user.UserDTO;
import com.imd.backend.api.dto.profile.ProfileCreateDTO;
import com.imd.backend.domain.entities.core.User;
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
    private final ProfileService profileService;

    public AuthService(
            UserService userService,
            @Lazy AuthenticationManager authenticationManager,
            JwtService jwtService,
            TuneUserDetailsService tuneUserDetailsService,
            PasswordEncoder passwordEncoder,
            ProfileService profileService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tuneUserDetailsService = tuneUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
    }

    public LoginResponse login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        TuneUserDetails tuneUserDetails = tuneUserDetailsService.loadUserByUsername(username);

        User user = this.userService.findUserByUsername(tuneUserDetails.getUsername());

        // Garante que existe perfil para o usuário
        if (user.getProfile() == null) {
            var createdProfile = profileService.create(new ProfileCreateDTO(user.getId(), null, null));
            user.setProfile(createdProfile);
        }

        String accessToken = jwtService.generateAccessToken(tuneUserDetails);
        UserDTO userReponse = new UserDTO(user.getId().toString(), user.getUsername(), user.getEmail(), user.getProfile().getId());

        return new LoginResponse(accessToken, userReponse);
    }

    public User register(String username, String email, String password) throws BusinessException {
        final var encryptedPassword = passwordEncoder.encode(password);
        final User userToCreate = User.builder().email(email)
                .password(encryptedPassword)
                .username(username).build();
        User created = userService.createUser(userToCreate);
        // Cria perfil padrão para evitar erros de login subsequentes
        profileService.create(new ProfileCreateDTO(created.getId(), null, null));
        return created;
    }
}
