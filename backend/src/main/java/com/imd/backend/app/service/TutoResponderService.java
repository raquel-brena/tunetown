package com.imd.backend.app.service;

import com.imd.backend.app.gateway.aiGateway.AiAgentGateway;
import com.imd.backend.app.service.core.BaseBotResponder;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.infra.configuration.BotProfileProvider;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TutoResponderService extends BaseBotResponder<Tuneet, Comment> {

    public TutoResponderService(
            AiAgentGateway aiGateway,
            BotProfileProvider botProfileProvider,
            CommentRepository commentRepository) {
        super(aiGateway, botProfileProvider, commentRepository);
    }

    @Override
    protected String getBotPersonality() {
        return "Descolado, otimista e fã de música; responde de forma leve, direta e com boas vibes";
    }

    @Override
    protected String getBotMentionHandle() {
        return "@tuto";
    }

    @Override
    protected Comment buildBotComment(Profile botProfile, Tuneet post, String responseText, LocalDateTime createdAt) {
        return Comment.builder()
                .post(post)
                .author(botProfile)
                .contentText(responseText)
                .createdAt(createdAt)
                .build();
    }
}
