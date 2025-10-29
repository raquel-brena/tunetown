package com.imd.backend.app.service;

import com.imd.backend.app.gateway.aiGateway.TunetownAiGateway;
import com.imd.backend.infra.configuration.TutoProfileRegistry;
import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TutoResponder {

    private static final Logger LOGGER = LoggerFactory.getLogger(TutoResponder.class);

    private final TunetownAiGateway aiGateway;
    private final TutoProfileRegistry tutoProfileRegistry;
    private final CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TutoResponder(
            TunetownAiGateway aiGateway,
            TutoProfileRegistry tutoProfileRegistry,
            CommentRepository commentRepository
    ) {
        this.aiGateway = aiGateway;
        this.tutoProfileRegistry = tutoProfileRegistry;
        this.commentRepository = commentRepository;
    }

    @Async
    public void generateResponseAsync(
            String tuneetId,
            String tuneetText,
            String tunableContent,
            String originalComment
    ) {
        try {
            String prompt = buildPrompt(tuneetText, tunableContent, originalComment);
            String response = aiGateway.askTuto(prompt);

            if (response == null || response.isBlank()) {
                return;
            }

            ProfileEntity tutoProfile = tutoProfileRegistry.getProfile();
            if (tutoProfile == null) {
                LOGGER.warn("Perfil do Tuto não pôde ser recuperado. Resposta automática não será criada.");
                return;
            }

            TuneetEntity tuneetRef = entityManager.getReference(TuneetEntity.class, tuneetId);

            CommentEntity tutoComment = CommentEntity.builder()
                    .tuneet(tuneetRef)
                    .author(tutoProfile)
                    .contentText(response.trim())
                    .createdAt(new Date())
                    .build();

            commentRepository.save(tutoComment);
        } catch (Exception ex) {
            LOGGER.error("Erro ao gerar resposta do Tuto para o tuneet {}", tuneetId, ex);
        }
    }

    private String buildPrompt(String tuneetText, String tunableContent, String commentContent) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você foi marcado em um comentário sobre um tuneet.\n");
        prompt.append("Conteúdo do tuneet: \"")
                .append(tuneetText)
                .append("\".\n");

        if (tunableContent != null && !tunableContent.isBlank()) {
            prompt.append("Informações do item associado: ")
                    .append(tunableContent)
                    .append(".\n");
        }

        prompt.append("Comentário do usuário: \"")
                .append(commentContent)
                .append("\"");

        return prompt.toString();
    }
}

