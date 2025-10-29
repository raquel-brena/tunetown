package com.imd.backend.app.gateway.aiGateway;

import com.imd.backend.api.dto.ai.QuestionDTO;
import com.imd.backend.api.dto.ai.TutoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TunetownAiGateway {

	private final AiFeignClient aiFeignClient;

	@Autowired
	public TunetownAiGateway(AiFeignClient aiFeignClient) {
		this.aiFeignClient = aiFeignClient;
	}

	public TutoResponseDTO askTuto(QuestionDTO question) {
		return aiFeignClient.tuto(question);
	}

	public String askTuto(String question) {
		var response = aiFeignClient.tuto(new QuestionDTO(question));
		return response.response();
	}
}

