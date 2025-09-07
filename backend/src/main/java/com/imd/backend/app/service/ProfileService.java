package com.imd.backend.app.service;

import com.imd.backend.domain.entities.Profile;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.mapper.ProfileMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements CrudService<String, Profile> {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile findById(String id) {
        ProfileEntity entity = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProfileEntity not found"));
        return ProfileMapper.toDomain(entity);
    }

    @Override
    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable)
                .map(ProfileMapper::toDomain);
    }

    @Override
    public Profile create(Profile profile) {
        ProfileEntity saved = profileRepository.save(ProfileMapper.toEntity(profile));
        return ProfileMapper.toDomain(saved);
    }

    @Override
    public Profile update(Profile profile) {
        ProfileEntity existing = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new NotFoundException("ProfileEntity not found"));

        if (profile.getBio() != null) existing.setBio(profile.getBio());
        if (profile.getAvatarUrl() != null) existing.setAvatarUrl(profile.getAvatarUrl());
        if (profile.getFavoriteSong() != null) existing.setFavoriteSong(profile.getFavoriteSong());

        ProfileEntity updated = profileRepository.save(existing);
        return ProfileMapper.toDomain(updated);
    }

    @Override
    public void delete(String id) {
        if (!profileRepository.existsById(id)) {
            throw new NotFoundException("ProfileEntity not found");
        }
        profileRepository.deleteById(id);
    }
}
