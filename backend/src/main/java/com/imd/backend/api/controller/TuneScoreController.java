package com.imd.backend.api.controller;

import com.imd.backend.api.dto.tunescore.TuneScoreRequest;
import com.imd.backend.api.dto.tunescore.TuneScoreResponse;
import com.imd.backend.app.service.TuneScoreService;
import com.imd.backend.domain.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/tunescore")
public class TuneScoreController {
    private final TuneScoreService tuneScoreService;

    @Autowired
    public TuneScoreController(TuneScoreService tuneScoreService) {
        this.tuneScoreService = tuneScoreService;
    }

    @PostMapping(path="/calculate")
    public ResponseEntity<TuneScoreResponse>  calculateTuneScore(@RequestBody TuneScoreRequest request) {
        try {
            var tunescore = tuneScoreService.calculateTuneScore(UUID.fromString(request.originUserId()), UUID.fromString(request.destinationUserId()));
            return ResponseEntity.ok(tunescore);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }

    }


}
