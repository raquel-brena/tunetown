package com.imd.backend.app.gateway.llmGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Ponto fixo para prover credenciais e modelo do LLM.
 * Permite trocar provedor/modelo em uma única implementação para ser usada por agent e TuneScore.
 */
@Component
public class BaseLLMProvider {

    private final String apiKey;
    private final String modelName;

    public BaseLLMProvider(
            @Value("${openai.api-key:${SPRING_AI_OPENAI_API_KEY:${OPENAI_API_KEY:}}}") String apiKey,
            @Value("${openai.model:gpt-4o-mini}") String modelName
    ) {
        if (!StringUtils.hasText(apiKey)) {
            throw new IllegalStateException("OpenAI API key not configured.");
        }
        this.apiKey = apiKey;
        this.modelName = modelName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getModelName() {
        return modelName;
    }
}
