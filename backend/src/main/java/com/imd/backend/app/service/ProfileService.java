package com.imd.backend.app.service;

import com.imd.backend.domain.entities.Profile;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.entity.FileEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.mapper.ProfileMapper;
import com.imd.backend.infra.persistence.jpa.repository.FileRepository;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

@Service
public class ProfileService implements CrudService<String, Profile> {

    private final ProfileRepository profileRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;

    public ProfileService(ProfileRepository profileRepository, FileRepository fileRepository, S3Service s3Service) {
        this.profileRepository = profileRepository;
        this.fileRepository = fileRepository;
        this.s3Service = s3Service;
    }

    public Profile updatePhoto(String id, MultipartFile file) throws IOException {
        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow();

        FileEntity photo = s3Service.uploadFile(file);
        fileRepository.save(photo);
        profile.setPhoto(photo);
        ProfileEntity savedProfile = profileRepository.save(profile);
        return ProfileMapper.toDomain(savedProfile);
    }

    public Profile findById(String id) {
        ProfileEntity entity = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProfileEntity not found"));

        if (entity.getPhoto() != null) {
            String avatarUrl = s3Service.generatePresignedUrl(entity.getPhoto().getUrl(), Duration.ofHours(1));
            entity.getPhoto().setUrl(avatarUrl);
        }

        return ProfileMapper.toDomain(entity);
    }

    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable)
                .map(entity -> {
                    if (entity.getPhoto() != null) {
                        String avatarUrl = s3Service.generatePresignedUrl(
                                entity.getPhoto().getUrl(), Duration.ofHours(1));
                        entity.getPhoto().setUrl(avatarUrl);
                    }
                    return ProfileMapper.toDomain(entity);
                });
    }

    public Profile create(Profile profile) {
        profile.setCreatedAt(new Date());
        ProfileEntity saved = profileRepository.save(ProfileMapper.toEntity(profile));
        return ProfileMapper.toDomain(saved);
    }

    public Profile update(Profile profile) {
        ProfileEntity existing = profileRepository.findById(profile.getId())
                .orElseThrow(() -> new NotFoundException("ProfileEntity not found"));

        if (profile.getUsername() != null) {
            existing.setUsername(profile.getUsername());
        }
        if (profile.getBio() != null) existing.setBio(profile.getBio());
        if (profile.getFavoriteSong() != null) existing.setFavoriteSong(profile.getFavoriteSong());

        ProfileEntity updated = profileRepository.save(existing);
        return ProfileMapper.toDomain(updated);
    }

    public void delete(String id) {
        if (!profileRepository.existsById(id)) {
            throw new NotFoundException("ProfileEntity not found");
        }
        profileRepository.deleteById(id);
    }
}
