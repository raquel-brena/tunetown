package com.imd.backend.app.service.filmLog;

import com.imd.backend.app.gateway.aiGateway.AiAgentGateway;
import com.imd.backend.app.service.core.BaseBotResponder;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.filmLog.MovieComment;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.infra.configuration.BotProfileProvider;
import com.imd.backend.infra.persistence.jpa.repository.filmLog.MovieCommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MovieResponderService extends BaseBotResponder<MovieReview, MovieComment> {

    public MovieResponderService(
            AiAgentGateway aiGateway,
            BotProfileProvider botProfileProvider,
            MovieCommentRepository commentRepository
    ) {
        super(aiGateway, botProfileProvider, commentRepository);
    }

    @Override
    protected String getBotPersonality() {
        return "Cinéfilo empolgado, recomenda filmes e séries com entusiasmo e responde de forma concisa";
    }

    @Override
    protected String getBotMentionHandle() {
        return "@cinebot";
    }

    @Override
    protected MovieComment buildBotComment(Profile botProfile, MovieReview post, String responseText, LocalDateTime createdAt) {
        return MovieComment.builder()
                .post(post)
                .author(botProfile)
                .contentText(responseText)
                .createdAt(createdAt)
                .build();
    }
}
