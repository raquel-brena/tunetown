package com.imd.backend.api.controller;

import com.imd.backend.api.dto.tunescore.TuneScoreResponse;
import com.imd.backend.app.service.TuneScoreService;
import com.imd.backend.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tunescore")
public class TuneScoreController {
    private final TuneScoreService tuneScoreService;

    public TuneScoreController(TuneScoreService tuneScoreService) {
        this.tuneScoreService = tuneScoreService;
    }

    @GetMapping(path = "/{originUsername}/{destinationUsername}")
    public ResponseEntity<TuneScoreResponse> calculateTuneScore(
            @PathVariable String originUsername,
            @PathVariable String destinationUsername
    ) {
        try {
            var tunescore = tuneScoreService.calculateTuneScore(originUsername, destinationUsername);
            return ResponseEntity.ok(tunescore);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
