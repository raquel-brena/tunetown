package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.api.dto.comment.CommentResponseDTO;
import com.imd.backend.api.dto.comment.CommentUpdateDTO;
import com.imd.backend.domain.entities.Comment;
import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;

public class CommentMapper {

    public static CommentResponseDTO toDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .tuneetId(comment.getTuneetId())
                .authorUsername(comment.getAuthorUsername())
                .contentText(comment.getContentText())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static Comment toDomain(CommentCreateDTO dto) {
        return new Comment(
                null,
                dto.getTuneetId().toString(),
                dto.getAuthorId(),
                null,
                dto.getContentText(),
                new java.util.Date()
        );
    }

    public static Comment toDomain(CommentUpdateDTO dto) {
        return new Comment(
                dto.getId(),
                null,
                null,
                null,
                dto.getContentText(),
                null
        );
    }

    public static Comment toDomain(CommentEntity entity) {
        ProfileEntity author = entity.getAuthor();
        return new Comment(
                entity.getId(),
                entity.getTuneet().getId(),
                author != null ? author.getId() : null,
                author != null && author.getUser() != null ? author.getUser().getUsername() : null,
                entity.getContentText(),
                entity.getCreatedAt()
        );
    }

    public static CommentEntity toEntity(Comment domain) {
        return CommentEntity.builder()
                .id(domain.getId())
                .contentText(domain.getContentText())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public static CommentEntity toEntity(CommentCreateDTO domain) {
        return CommentEntity.builder()
                .contentText(domain.getContentText())
                .build();
    }
}
