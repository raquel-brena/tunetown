package com.imd.backend.api.controller;

import com.imd.backend.api.dto.agent.AgentRequest;
import com.imd.backend.app.service.AgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
public class AiAgentController {

    private final AgentService agentService;

    public AiAgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody AgentRequest request) {
        String response = agentService.chat(request.message());
        return ResponseEntity.ok(response);
    }
}
