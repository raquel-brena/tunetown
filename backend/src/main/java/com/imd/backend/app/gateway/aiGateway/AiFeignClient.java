package com.imd.backend.app.gateway.aiGateway;

import com.imd.backend.api.dto.ai.QuestionDTO;
import com.imd.backend.api.dto.ai.TutoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aiClient", url = "${ai.service.url:http://ai:5000}")
public interface AiFeignClient {

    @PostMapping("/tuto")
    TutoResponseDTO tuto(@RequestBody QuestionDTO question);
}
