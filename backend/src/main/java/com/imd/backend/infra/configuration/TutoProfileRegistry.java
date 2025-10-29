package com.imd.backend.infra.configuration;

import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.user.UserJPA;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TutoProfileRegistry {

    private static final String TUTO_USERNAME = "tuto";
    private static final String TUTO_EMAIL = "tuto@tunetown.app";

    private final ProfileRepository profileRepository;
    private final UserJPA userRepository;

    private ProfileEntity cachedProfile;

    public TutoProfileRegistry(ProfileRepository profileRepository, UserJPA userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public synchronized ProfileEntity getProfile() {
        if (cachedProfile == null || cachedProfile.getId() == null) {
            cachedProfile = findOrCreateProfile();
        }
        return cachedProfile;
    }

    public String getProfileId() {
        return getProfile().getId();
    }

    private ProfileEntity findOrCreateProfile() {
        return profileRepository.findByUserUsername(TUTO_USERNAME)
                .map(this::ensureUserLink)
                .orElseGet(this::createProfileWithUser);
    }

    private ProfileEntity ensureUserLink(ProfileEntity profile) {
        UserEntity user = profile.getUser();
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

    private ProfileEntity createProfileWithUser() {
        UserEntity user = userRepository.findByUsername(TUTO_USERNAME)
                .orElseGet(this::createUser);

        ProfileEntity profile = ProfileEntity.builder()
                .user(user)
                .bio("Assistente virtual da Tunetown")
                .createdAt(new Date())
                .build();

        profile = profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);

        return profile;
    }

    private UserEntity createUser() {
        UserEntity user = new UserEntity(UUID.randomUUID().toString(), TUTO_EMAIL, TUTO_USERNAME, null);
        return userRepository.save(user);
    }
}

