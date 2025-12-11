package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.api.dto.comment.CommentResponseDTO;
import com.imd.backend.api.dto.comment.CommentUpdateDTO;
import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;

public class CommentMapper {

    public static CommentResponseDTO toDTO(BaseComment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .tuneetId(comment.getPost().getId())
                .authorUsername(comment.getAuthor().getUser().getUsername())
                .contentText(comment.getContentText())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static BaseComment toDomain(CommentDTO dto) {
        Tuneet tuneet = new Tuneet();
        tuneet.setId(dto.getTuneetId());

        Profile author = new Profile();
        author.setId(dto.getAuthorId());

        return Comment.create(
                author,
                tuneet,
                dto.getContentText()
        );
    }

    public static BaseComment toDomain(CommentUpdateDTO dto) {
        return Comment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())
                .build();
    }

    public static BaseComment toEntity(CommentDTO dto) {
        return Comment.builder()
                .contentText(dto.getContentText())
                .build();
    }
}
