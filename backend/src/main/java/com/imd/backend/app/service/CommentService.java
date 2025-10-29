package com.imd.backend.app.service;

import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.domain.entities.Comment;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.mapper.CommentMapper;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.tuneet.TuneetJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final TuneetJpaRepository tuneetRepository;
    private final ProfileRepository profileRepository;
    private final TutoResponder tutoResponder;

    public Page<Comment> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDomain);
    }

    public Comment findById(Long id) {
        return repository.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
    }

    public Comment create(CommentCreateDTO dto) {
        Tuneet tuneet = tuneetRepository.findById(UUID.fromString(dto.getTuneetId()))
                .orElseThrow();
        ProfileEntity author = profileRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Autor não encontrado."));

        CommentEntity entity = CommentMapper.toEntity(dto);
        entity.setAuthor(author);
        entity.setTuneet(TuneetJpaMapper.fromTuneetDomain(tuneet));
        entity.setCreatedAt(new Date());

        CommentEntity savedComment = repository.save(entity);

        if (containsTutoMention(dto.getContentText())) {
            String tunableSummary;
            try {
                tunableSummary = tuneet.getTunableContent();
            } catch (Exception ex) {
                tunableSummary = null;
            }
            tutoResponder.generateResponseAsync(
                    savedComment.getTuneet().getId(),
                    tuneet.getTextContent(),
                    tunableSummary,
                    dto.getContentText()
            );
        }

        return toDomain(savedComment);
    }

    public Comment update(Comment comment) {
        CommentEntity entity = repository.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
        entity.setContentText(comment.getContentText());
        CommentEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Comentário não encontrado.");
        }
        repository.deleteById(id);
    }

    public Page<Comment> findByTuneetId(String tuneetId, Pageable pageable) {
        Page<CommentEntity> entities = repository.findByTuneetId(tuneetId, pageable);
        if (!entities.hasContent()) {
            throw new NotFoundException("Nenhum comentário encontrado para este tuneet.");
        }
        return entities.map(this::toDomain);
    }

    private Comment toDomain(CommentEntity entity) {
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

    private boolean containsTutoMention(String content) {
        return content != null && content.toLowerCase().contains("@tuto");
    }
}
