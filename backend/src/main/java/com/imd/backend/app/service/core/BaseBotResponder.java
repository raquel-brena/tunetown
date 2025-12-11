package com.imd.backend.app.service.core;

import com.imd.backend.app.gateway.aiGateway.AiAgentGateway;
import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.infra.configuration.BotProfileProvider;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

public abstract class BaseBotResponder<P extends BasePost, C extends BaseComment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseBotResponder.class);

    private final AiAgentGateway aiGateway;
    private final BotProfileProvider botProfileProvider;
    private final BaseCommentRepository<C> commentRepository;

    protected BaseBotResponder(
            AiAgentGateway aiGateway,
            BotProfileProvider botProfileProvider,
            BaseCommentRepository<C> commentRepository) {
        this.aiGateway = aiGateway;
        this.botProfileProvider = botProfileProvider;
        this.commentRepository = commentRepository;
    }

    /** Handle ou nome mencionado no comentário (ex: "@tuto"). */
    protected abstract String getBotMentionHandle();

    /** Descrição curta da personalidade do bot, definida pela instância. */
    protected abstract String getBotPersonality();

    /** Constrói o comentário do bot para o post específico. */
    protected abstract C buildBotComment(Profile botProfile, P post, String responseText, LocalDateTime createdAt);

    public boolean isMentioned(String content) {
        if (content == null || content.isBlank()) {
            return false;
        }
        return content.toLowerCase().contains(getBotMentionHandle().toLowerCase());
    }

    @Async
    public void generateResponseAsync(
            P post,
            String itemSummary,
            String originalComment) {
        try {
            String prompt = buildPrompt(post.getTextContent(), itemSummary, originalComment);
            String response = aiGateway.chat(prompt);

            if (response == null || response.isBlank()) {
                return;
            }

            Profile botProfile = botProfileProvider.getProfile();
            if (botProfile == null) {
                LOGGER.warn("Perfil do bot não pôde ser recuperado. Resposta automática não será criada.");
                return;
            }

            C botComment = buildBotComment(botProfile, post, response.trim(), LocalDateTime.now());
            commentRepository.save(botComment);
        } catch (Exception ex) {
            LOGGER.error("Erro ao gerar resposta do bot para o post {}", post.getId(), ex);
        }
    }

    private String buildPrompt(String postText, String itemSummary, String commentContent) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Você é o bot oficial da plataforma. ")
                .append("Seu handle é ").append(getBotMentionHandle()).append(". ");

        String personality = getBotPersonality();
        if (personality != null && !personality.isBlank()) {
            prompt.append("Personalidade: ").append(personality).append(". ");
        }

        prompt.append(
                "Você foi marcado em um comentário sobre um post e deve responder de forma breve e alinhada à sua personalidade.\n")
                .append("Conteúdo do post: \"").append(postText).append("\".\n");

        if (itemSummary != null && !itemSummary.isBlank()) {
            prompt.append("Informações adicionais: ")
                    .append(itemSummary)
                    .append(".\n");
        }

        prompt.append("Comentário do usuário: \"")
                .append(commentContent)
                .append("\"");

        return prompt.toString();
    }
}
