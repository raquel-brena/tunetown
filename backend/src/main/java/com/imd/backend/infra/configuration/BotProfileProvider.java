package com.imd.backend.infra.configuration;

import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Provê (ou cria) o perfil do bot a partir de configurações.
 */
@Component
public class BotProfileProvider {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private final String botUsername;
    private final String botEmail;
    private final String botBio;

    private Profile cachedProfile;

    public BotProfileProvider(
            ProfileRepository profileRepository,
            UserRepository userRepository,
            @Value("${bot.username:bot}") String botUsername,
            @Value("${bot.email:bot@example.com}") String botEmail,
            @Value("${bot.bio:Assistente virtual}") String botBio
    ) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.botUsername = botUsername;
        this.botEmail = botEmail;
        this.botBio = botBio;
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
        return profileRepository.findByUserUsername(botUsername)
                .map(this::ensureUserLink)
                .orElseGet(this::createProfileWithUser);
    }

    private Profile ensureUserLink(Profile profile) {
        User user = profile.getUser();
        if (user == null || user.getUsername() == null) {
            user = userRepository.findByUsername(botUsername)
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
        User user = userRepository.findByUsername(botUsername)
                .orElseGet(this::createUser);

        Profile profile = Profile.builder()
                .user(user)
                .bio(botBio)
                .createdAt(LocalDateTime.now())
                .build();

        profile = profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);

        return profile;
    }

    private User createUser() {
        User user = new User(UUID.randomUUID().toString(), botEmail, botUsername, null);
        return userRepository.save(user);
    }
}
