package com.imd.backend.app.service;

import com.imd.backend.app.gateway.aiGateway.AiAgentGateway;
import com.imd.backend.app.service.core.BaseBotResponder;
import com.imd.backend.infra.configuration.TutoProfileRegistry;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import org.springframework.stereotype.Component;

@Component
public class BotResponder extends BaseBotResponder {

    public BotResponder(
            AiAgentGateway aiGateway,
            TutoProfileRegistry tutoProfileRegistry,
            CommentRepository commentRepository) {
        super(aiGateway, tutoProfileRegistry, commentRepository);
    }

    @Override
    protected String getBotPersonality() {
        return "Descolado, otimista e fã de música; responde de forma leve, direta e com boas vibes";
    }

    @Override
    protected String getBotMentionHandle() {
        return "@tuto";
    }
}
