package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BaseSimilarityController;
import com.imd.backend.api.dto.tunescore.TuneScoreResponse;
import com.imd.backend.app.service.TuneScoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tunescore")
public class TuneScoreController extends BaseSimilarityController<TuneScoreResponse> {

    public TuneScoreController(TuneScoreService tuneScoreService) {
        super(tuneScoreService);
    }
}
