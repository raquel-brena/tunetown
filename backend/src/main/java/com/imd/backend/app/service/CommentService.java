package com.imd.backend.app.service;

import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.domain.entities.Comment;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TuneetResume;
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

import java.util.UUID;


@Service
public class CommentService {

    private final CommentRepository repository;
    private final TuneetJpaRepository tuneetRepository;
    private final ProfileRepository profileRepository;

    public CommentService(CommentRepository repository, TuneetJpaRepository tuneetRepository, ProfileRepository profileRepository) {
        this.repository = repository;
        this.tuneetRepository = tuneetRepository;
        this.profileRepository = profileRepository;
    }

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
        return toDomain(repository.save(entity));
    }

    public Comment update(Comment comment) {
        var entity = repository.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
        entity.setContentText(comment.getContentText());
        return toDomain(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Comentário não encontrado.");
        }
        repository.deleteById(id);
    }

    public Page<Comment> findByTuneetId(Long tuneetId, Pageable pageable) {
        Page<CommentEntity> entities = repository.findByTuneetId(tuneetId, pageable);

        if (!entities.hasContent()) {
            throw new NotFoundException("Nenhum comentário encontrado para este tuneet.");
        }

        return entities.map(CommentMapper::toDomain);
    }

    private Comment toDomain(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getTuneet().getId(),
                entity.getAuthor().getId(),
                entity.getContentText(),
                entity.getCreatedAt()
        );
    }
}
