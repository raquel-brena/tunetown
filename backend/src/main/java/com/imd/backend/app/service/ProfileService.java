package com.imd.backend.app.service;

import com.imd.backend.api.dto.profile.ProfileCreateDTO;
import com.imd.backend.domain.entities.core.MediaFile;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.mapper.ProfileMapper;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FileService fileService;

    public ProfileService(ProfileRepository profileRepository, FileService fileService) {
        this.profileRepository = profileRepository;
        this.fileService = fileService;
    }

    public Profile findById(String id) {
        Profile entity = findEntityById(id);
        fileService.applyPresignedUrl(entity);
        return entity;
    }

    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable)
                .map(entity -> {
                    fileService.applyPresignedUrl(entity);
                    return entity;
                });
    }

    public Profile create(ProfileCreateDTO profile) {
        Profile saved = profileRepository.save(ProfileMapper.toEntity(profile));
        return ProfileMapper.toDomain(saved);
    }

    public Profile update(Profile profile) {
        Profile existing = findEntityById(profile.getId());

        if (profile.getBio() != null) existing.setBio(profile.getBio());
        if (profile.getFavoriteSong() != null) existing.setFavoriteSong(profile.getFavoriteSong());

        return profileRepository.save(existing);
    }

    public void delete(String id) {
        if (!profileRepository.existsById(id)) {
            throw new NotFoundException("ProfileEntity not found");
        }
        profileRepository.deleteById(id);
    }

    public Profile updatePhoto(String id, MultipartFile file) throws IOException {
        Profile profile = findEntityById(id);

        if (profile.getPhoto() != null) {
            fileService.delete(profile.getPhoto());
        }

        MediaFile photo = fileService.create(file);
        profile.setPhoto(photo);
        Profile savedProfile = profileRepository.save(profile);

        fileService.applyPresignedUrl(savedProfile);

        return ProfileMapper.toDomain(savedProfile);
    }

    public Profile deletePhoto(String id) throws IOException {
        Profile profile = findEntityById(id);

        if (profile.getPhoto() != null) {
            fileService.delete(profile.getPhoto());
            profile.setPhoto(null);
            profileRepository.save(profile);
        }

        return ProfileMapper.toDomain(profile);
    }

    public Profile findEntityById(String id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile de id " + id + " não encontrado."));
    }
}
