package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.api.dto.comment.CommentResponseDTO;
import com.imd.backend.api.dto.comment.CommentUpdateDTO;
import com.imd.backend.domain.entities.Comment;
import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;

public class CommentMapper {

    public static CommentResponseDTO toDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .tuneetId(comment.getTuneetId())
                .authorId(comment.getAuthorId())
                .contentText(comment.getContentText())
                .createdAt(comment.getCreatedAt())
                .build();
    }


    public static Comment toDomain(CommentCreateDTO dto) {
        return new Comment(
                null,
                dto.getTuneetId(),
                dto.getAuthorId(),
                dto.getContentText(),
                new java.util.Date()
        );
    }

    public static Comment toDomain(CommentUpdateDTO dto) {
        return new Comment(
                dto.getId(),
                null,
                null,
                dto.getContentText(),
                null
        );
    }

    public static Comment toDomain(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getTuneet().getId(),
                entity.getAuthor().getId(),
                entity.getContentText(),
                entity.getCreatedAt()
        );
    }

    public static CommentEntity toEntity(Comment domain) {
        if (domain == null) return null;

        CommentEntity commentEntity = CommentEntity.builder()
                .id(domain.getId())
                .contentText(domain.getContentText())
                .createdAt(domain.getCreatedAt())
                .build();

        if (domain.getAuthorId() != null) {
            var author = new com.imd.backend.infra.persistence.jpa.entity.ProfileEntity();
            author.setId(domain.getAuthorId());
            commentEntity.setAuthor(author);
        }

        return commentEntity;
    }


    public static CommentEntity toEntity(CommentCreateDTO domain) {
        return CommentEntity.builder()
                .contentText(domain.getContentText())
                .build();
    }
}
