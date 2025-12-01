package com.imd.backend.app.service;

import com.imd.backend.domain.entities.core.MediaFile;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final String[] ALLOWED_TYPES = {"image/jpeg", "image/png", "image/jpg"};

    public FileService(FileRepository fileRepository, S3Service s3Service) {
        this.fileRepository = fileRepository;
        this.s3Service = s3Service;
    }

    public MediaFile create(MultipartFile file) throws IOException {
        validateAvatar(file);
        MediaFile photo = s3Service.uploadFile(file);
        return fileRepository.save(photo);
    }

    public void delete(MediaFile fileEntity) {
        if (fileEntity != null) {
            s3Service.deleteFile(fileEntity.getUrl());
            fileRepository.delete(fileEntity);
        }
    }

    public void applyPresignedUrl(Profile entity) {
        if (entity != null && entity.getPhoto() != null) {
            String avatarUrl = s3Service.generatePresignedUrl(
                    entity.getPhoto().getFileName(),
                    Duration.ofHours(36)
            );
            entity.getPhoto().setUrl(avatarUrl);
        }
    }

    public String applyPresignedUrl(String filename) {
        if (filename != null) {
            return this.s3Service.generatePresignedUrl(
                    filename,
                    Duration.ofHours(36)
            );
        }
        return "";
    }

    private void validateAvatar(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo do avatar não pode estar vazio.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("O avatar deve ter no máximo 5MB.");
        }

        String contentType = file.getContentType();
        boolean allowed = false;
        if (contentType != null) {
            for (String type : ALLOWED_TYPES) {
                if (contentType.equalsIgnoreCase(type)) {
                    allowed = true;
                    break;
                }
            }
        }

        if (!allowed) {
            throw new IllegalArgumentException("Formato de avatar inválido. Use JPG ou PNG.");
        }
    }
}
