package com.imd.backend.app.service;

import com.imd.backend.app.gateway.aiGateway.AiAgentGateway;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private final AiAgentGateway aiAgentGateway;

    public AgentService(AiAgentGateway aiAgentGateway) {
        this.aiAgentGateway = aiAgentGateway;
    }

    public String chat(String message) {
        return aiAgentGateway.chat(message);
    }
}
