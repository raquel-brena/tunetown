package com.imd.backend.infra.persistence.jpa.mapper;

import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.api.dto.comment.CommentResponseDTO;
import com.imd.backend.api.dto.comment.CommentUpdateDTO;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;

public class CommentMapper {

    public static CommentResponseDTO toDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .tuneetId(comment.getTuneet().getId())
                .authorUsername(comment.getAuthor().getUser().getUsername())
                .contentText(comment.getContentText())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static Comment toDomain(CommentCreateDTO dto) {
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

    public static Comment toDomain(CommentUpdateDTO dto) {
        return Comment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText()).build();
    }


    public static Comment toEntity(CommentCreateDTO domain) {
        return Comment.builder()
                .contentText(domain.getContentText())
                .build();
    }
}
