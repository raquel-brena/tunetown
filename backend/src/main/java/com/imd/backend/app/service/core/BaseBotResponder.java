package com.imd.backend.app.service.core;

import com.imd.backend.app.gateway.aiGateway.AiAgentGateway;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.infra.configuration.TutoProfileRegistry;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

/**
 * PONTO FIXO DO FRAMEWORK
 * Serviço base para respostas automáticas do bot mencionado em comentários.
 * Variações perceptíveis: identidade (nome/handle) e personalidade.
 */
public abstract class BaseBotResponder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseBotResponder.class);

    private final AiAgentGateway aiGateway;
    private final TutoProfileRegistry tutoProfileRegistry;
    private final CommentRepository commentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    protected BaseBotResponder(
            AiAgentGateway aiGateway,
            TutoProfileRegistry tutoProfileRegistry,
            CommentRepository commentRepository
    ) {
        this.aiGateway = aiGateway;
        this.tutoProfileRegistry = tutoProfileRegistry;
        this.commentRepository = commentRepository;
    }

    /**
     * Texto curto descrevendo a personalidade/voz do bot.
     */
    protected abstract String getBotPersonality();

    /**
     * Handle ou nome mencionado no comentário (ex: "@tuto").
     */
    protected abstract String getBotMentionHandle();

    public boolean isMentioned(String content) {
        if (content == null || content.isBlank()) {
            return false;
        }
        return content.toLowerCase().contains(getBotMentionHandle().toLowerCase());
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
            String response = aiGateway.chat(prompt);

            if (response == null || response.isBlank()) {
                return;
            }

            Profile tutoProfile = tutoProfileRegistry.getProfile();
            if (tutoProfile == null) {
                LOGGER.warn("Perfil do bot não pôde ser recuperado. Resposta automática não será criada.");
                return;
            }

            Tuneet tuneetRef = entityManager.getReference(Tuneet.class, tuneetId);

            Comment tutoComment = Comment.builder()
                    .post(tuneetRef)
                    .author(tutoProfile)
                    .contentText(response.trim())
                    .createdAt(LocalDateTime.now())
                    .build();

            commentRepository.save(tutoComment);
        } catch (Exception ex) {
            LOGGER.error("Erro ao gerar resposta do bot para o tuneet {}", tuneetId, ex);
        }
    }

    private String buildPrompt(String tuneetText, String tunableContent, String commentContent) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é o bot oficial da plataforma. ")
                .append("Seu handle é ").append(getBotMentionHandle()).append(". ")
                .append("Personalidade: ").append(getBotPersonality()).append(".\n")
                .append("Foi marcado em um comentário sobre um post musical e deve responder de forma breve e amigável.\n")
                .append("Conteúdo do tuneet: \"").append(tuneetText).append("\".\n");

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
