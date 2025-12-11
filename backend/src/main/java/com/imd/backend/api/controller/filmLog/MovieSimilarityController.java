package com.imd.backend.api.controller.filmLog;

import com.imd.backend.api.controller.core.BaseSimilarityController;
import com.imd.backend.api.dto.similarity.SimilarityResponse;
import com.imd.backend.app.service.filmLog.MovieSimilarityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie-similarity")
public class MovieSimilarityController extends BaseSimilarityController<SimilarityResponse> {

    public MovieSimilarityController(MovieSimilarityService service) {
        super(service);
    }
}
