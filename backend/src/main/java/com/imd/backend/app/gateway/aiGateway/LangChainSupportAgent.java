package com.imd.backend.app.gateway.aiGateway;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface LangChainSupportAgent {

    @SystemMessage("""
            Você é um assistente que pode consultar o banco via ferramenta db_query (somente leitura).
            Use SQL simples para buscar dados quando necessário.
            Sempre explique brevemente a resposta e cite que usou a consulta quando aplicável.
            Nunca modifique dados.
            """)
    String chat(@UserMessage String message);
}
