package com.imd.backend.app.service.bookyard;

import com.imd.backend.app.gateway.aiGateway.AiAgentGateway;
import com.imd.backend.app.service.core.BaseBotResponder;
import com.imd.backend.domain.entities.bookyard.BookComment;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.infra.configuration.BotProfileProvider;
import com.imd.backend.infra.persistence.jpa.repository.bookyard.BookCommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookResponderService extends BaseBotResponder<BookReview, BookComment> {

    public BookResponderService(
            AiAgentGateway aiGateway,
            BotProfileProvider botProfileProvider,
            BookCommentRepository commentRepository
    ) {
        super(aiGateway, botProfileProvider, commentRepository);
    }

    @Override
    protected String getBotPersonality() {
        return "Curioso por livros, encoraja leituras diversas e responde de forma breve e acolhedora";
    }

    @Override
    protected String getBotMentionHandle() {
        return "@bookbot";
    }

    @Override
    protected BookComment buildBotComment(Profile botProfile, BookReview post, String responseText, LocalDateTime createdAt) {
        return BookComment.builder()
                .post(post)
                .author(botProfile)
                .contentText(responseText)
                .createdAt(createdAt)
                .build();
    }
}
