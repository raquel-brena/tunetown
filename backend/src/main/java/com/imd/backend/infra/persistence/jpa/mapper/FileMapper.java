package com.imd.backend.infra.persistence.jpa.mapper;


import com.imd.backend.domain.entities.File;
import com.imd.backend.infra.persistence.jpa.entity.FileEntity;

public class FileMapper {

    public static FileEntity toDomain(FileEntity file) {
        if (file == null) return null;

        return FileEntity.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .url(file.getUrl())
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
    }

    public static File toEntity(FileEntity entity) {
        if (entity == null) return null;

        File file = new File();
        file.setId(entity.getId());
        file.setFileName(entity.getFileName());
        file.setUrl(entity.getUrl());
        file.setContentType(entity.getContentType());
        file.setSize(entity.getSize());
        return file;
    }
}
