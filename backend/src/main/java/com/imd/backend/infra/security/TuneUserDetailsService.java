package com.imd.backend.infra.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.imd.backend.domain.entities.User;
import com.imd.backend.domain.repository.UserRepository;

@Service
public class TuneUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public TuneUserDetailsService(
        @Qualifier("UserJpaRepository") UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public TuneUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new TuneUserDetails(user);
    }
}
