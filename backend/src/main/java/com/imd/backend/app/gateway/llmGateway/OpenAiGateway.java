package com.imd.backend.app.gateway.llmGateway;

import com.imd.backend.domain.exception.BusinessException;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OpenAiGateway {

    private final OpenAiChatModel chatModel;

    @Autowired
    public OpenAiGateway(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public <T> T structuredCall(String prompt, Class<T> structuredResponse) {
        var converter = new BeanOutputConverter<>(structuredResponse);
        var schema = converter.getJsonSchema();

        var response = chatModel.call(new Prompt(prompt, OpenAiChatOptions.builder()
                .model("gpt-4.1-mini")
                .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, schema))
                .build()));

        var textResponse = response.getResult().getOutput().getText();
        if (textResponse == null) {
            throw new BusinessException("Não foi possível calular o TuneScore.");
        }

        return converter.convert(textResponse);
    }


}
