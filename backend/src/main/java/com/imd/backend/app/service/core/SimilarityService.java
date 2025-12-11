package com.imd.backend.app.service.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imd.backend.app.gateway.llmGateway.OpenAiGateway;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.core.PostItem;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * PONTO FIXO DO FRAMEWORK
 * Serviço base para calcular similaridade entre dois usuários olhando seus
 * posts recentes.
 * Cada aplicação customiza o prompt e o mapeamento da resposta.
 */
public abstract class SimilarityService<P extends BasePost, I extends PostItem, R> {

        private static final int DEFAULT_POST_LIMIT = 50;

        private final BasePostRepository<P, I> postRepository;
        private final OpenAiGateway openAiGateway;
        private final int postLimit;

        protected SimilarityService(
                        BasePostRepository<P, I> postRepository,
                        OpenAiGateway openAiGateway) {
                this(postRepository, openAiGateway, DEFAULT_POST_LIMIT);
        }

        protected SimilarityService(
                        BasePostRepository<P, I> postRepository,
                        OpenAiGateway openAiGateway,
                        int postLimit) {
                this.postRepository = postRepository;
                this.openAiGateway = openAiGateway;
                this.postLimit = postLimit;
        }

        /**
         * Recupera posts de ambos usuários, monta o prompt customizado e retorna a
         * resposta mapeada.
         */
        public R calculateSimilarity(String firstUserId, String secondUserId) {
                Pageable pageable = PageRequest.of(0, postLimit, Sort.by(Sort.Direction.DESC, "createdAt"));

                List<P> firstUserPosts = postRepository.findByAuthorId(firstUserId, pageable).getContent();
                List<P> secondUserPosts = postRepository.findByAuthorId(secondUserId, pageable).getContent();

                String prompt = buildPrompt(firstUserId, secondUserId, firstUserPosts, secondUserPosts);
                SimilarityStructuredResponse response = openAiGateway.structuredCall(prompt,
                                SimilarityStructuredResponse.class);

                return buildResponse(firstUserId, secondUserId, response);
        }

        protected abstract String buildPrompt(
                        String firstUserId,
                        String secondUserId,
                        List<P> firstUserPosts,
                        List<P> secondUserPosts);

        protected abstract R buildResponse(
                        String firstUserId,
                        String secondUserId,
                        SimilarityStructuredResponse response);

        public record SimilarityStructuredResponse(
                        @JsonProperty(required = true, value = "score") float score,
                        @JsonProperty(required = true, value = "message") String message) {
        }
}
