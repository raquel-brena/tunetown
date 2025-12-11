package com.imd.backend.app.gateway.aiGateway;

import com.imd.backend.app.gateway.llmGateway.BaseLLMProvider;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Component;

/**
 * Gateway responsável por montar o agente LangChain4j com ferramentas.
 */
@Component
public class AiAgentGateway {

    private final LangChainSupportAgent agent;

    public AiAgentGateway(
            DbQueryTool dbQueryTool,
            BaseLLMProvider llmProvider
    ) {
        ChatLanguageModel chatModel = OpenAiChatModel.builder()
                .apiKey(llmProvider.getApiKey())
                .modelName(llmProvider.getModelName())
                .build();

        this.agent = AiServices.builder(LangChainSupportAgent.class)
                .chatLanguageModel(chatModel)
                .tools(dbQueryTool)
                .build();
    }

    public String chat(String message) {
        return agent.chat(message);
    }
}
