package com.imd.backend.api.controller.core;

import com.imd.backend.app.service.core.SimilarityService;
import com.imd.backend.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * PONTO FIXO DO FRAMEWORK
 * Controller genérico para cálculo de similaridade entre dois perfis.
 * As subclasses apenas definem o caminho base e o tipo de resposta.
 */
public abstract class BaseSimilarityController<R> {

    private final SimilarityService<?, ?, R> similarityService;

    protected BaseSimilarityController(SimilarityService<?, ?, R> similarityService) {
        this.similarityService = similarityService;
    }

    @GetMapping("/{originUserId}/{destinationUserId}")
    public ResponseEntity<R> calculateSimilarity(
            @PathVariable String originUserId,
            @PathVariable String destinationUserId) {
        try {
            R result = similarityService.calculateSimilarity(originUserId, destinationUserId);
            return ResponseEntity.ok(result);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
