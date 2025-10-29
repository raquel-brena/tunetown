package com.imd.backend.app.service;

import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.app.gateway.aiGateway.TunetownAiGateway;
import com.imd.backend.domain.entities.Comment;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.configuration.TutoProfileRegistry;
import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.mapper.CommentMapper;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.tuneet.TuneetJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository repository;
    private final TuneetJpaRepository tuneetRepository;
    private final ProfileRepository profileRepository;
    private final TunetownAiGateway aiGateway;
    private final TutoProfileRegistry tutoProfileRegistry;

    public CommentService(
            CommentRepository repository,
            TuneetJpaRepository tuneetRepository,
            ProfileRepository profileRepository,
            TunetownAiGateway aiGateway,
            TutoProfileRegistry tutoProfileRegistry
    ) {
        this.repository = repository;
        this.tuneetRepository = tuneetRepository;
        this.profileRepository = profileRepository;
        this.aiGateway = aiGateway;
        this.tutoProfileRegistry = tutoProfileRegistry;
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
        entity.setCreatedAt(new Date());

        CommentEntity savedComment = repository.save(entity);

        if (containsTutoMention(dto.getContentText())) {
            triggerTutoResponse(savedComment, tuneet, dto.getContentText());
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
        return new Comment(
                entity.getId(),
                entity.getTuneet().getId(),
                entity.getAuthor().getId(),
                entity.getContentText(),
                entity.getCreatedAt()
        );
    }

    private boolean containsTutoMention(String content) {
        return content != null && content.toLowerCase().contains("@tuto");
    }

    private void triggerTutoResponse(CommentEntity commentEntity, Tuneet tuneet, String originalComment) {
        try {
            String prompt = buildPrompt(tuneet, originalComment);
            String response = aiGateway.askTuto(prompt);

            if (response == null || response.isBlank()) {
                return;
            }

            ProfileEntity tutoProfile = tutoProfileRegistry.getProfile();
            if (tutoProfile == null) {
                LOGGER.warn("Perfil do Tuto não pôde ser recuperado. Resposta automática não será criada.");
                return;
            }

            CommentEntity tutoComment = CommentEntity.builder()
                    .tuneet(commentEntity.getTuneet())
                    .author(tutoProfile)
                    .contentText(response.trim())
                    .createdAt(new Date())
                    .build();

            repository.save(tutoComment);
        } catch (Exception ex) {
            LOGGER.error("Erro ao gerar resposta do Tuto para o comentário {}", commentEntity.getId(), ex);
        }
    }

    private String buildPrompt(Tuneet tuneet, String commentContent) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você foi marcado em um comentário sobre um tuneet.\n");
        prompt.append("Conteúdo do tuneet: \"")
                .append(tuneet.getTextContent())
                .append("\".\n");

        if (tuneet.getTunableItem() != null) {
            prompt.append("Informações do item associado: ")
                    .append(tuneet.getTunableContent())
                    .append(".\n");
        }

        prompt.append("Comentário do usuário: \"")
                .append(commentContent)
                .append("\"");

        return prompt.toString();
    }
}
