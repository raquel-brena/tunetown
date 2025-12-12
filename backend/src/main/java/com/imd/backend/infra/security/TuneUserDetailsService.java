package com.imd.backend.infra.security;

import com.imd.backend.domain.entities.core.User;
import com.imd.backend.infra.persistence.jpa.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TuneUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public TuneUserDetailsService(
         UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public CoreUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CoreUserDetails(user);
    }
}
