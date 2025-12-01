package com.imd.backend.infra.configuration;

import org.springframework.stereotype.Component;

import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.repository.ProfileRepository;
import com.imd.backend.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TutoProfileRegistry {

    private static final String TUTO_USERNAME = "tuto";
    private static final String TUTO_EMAIL = "tuto@tunetown.app";

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private Profile cachedProfile;

    public TutoProfileRegistry(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public synchronized Profile getProfile() {
        if (cachedProfile == null || cachedProfile.getId() == null) {
            cachedProfile = findOrCreateProfile();
        }
        return cachedProfile;
    }

    public String getProfileId() {
        return getProfile().getId();
    }

    private Profile findOrCreateProfile() {
        return profileRepository.findByUserUsername(TUTO_USERNAME)
                .map(this::ensureUserLink)
                .orElseGet(this::createProfileWithUser);
    }

    private Profile ensureUserLink(Profile profile) {
        User user = profile.getUser();
        if (user == null || user.getUsername() == null) {
            user = userRepository.findByUsername(TUTO_USERNAME)
                    .orElseGet(this::createUser);
            profile.setUser(user);
            profile = profileRepository.save(profile);
        }

        if (user.getProfile() == null || !profile.getId().equals(user.getProfile().getId())) {
            user.setProfile(profile);
            userRepository.save(user);
        }

        return profile;
    }

    private Profile createProfileWithUser() {
        User user = userRepository.findByUsername(TUTO_USERNAME)
                .orElseGet(this::createUser);

        Profile profile = Profile.builder()
                .user(user)
                .bio("Assistente virtual da Tunetown")
                .createdAt(LocalDateTime.now())
                .build();

        profile = profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);

        return profile;
    }

    private User createUser() {
        User user = new User(UUID.randomUUID().toString(), TUTO_EMAIL, TUTO_USERNAME, null);
        return userRepository.save(user);
    }
}

