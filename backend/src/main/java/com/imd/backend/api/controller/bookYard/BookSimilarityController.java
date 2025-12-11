package com.imd.backend.api.controller.bookYard;

import com.imd.backend.api.controller.core.BaseSimilarityController;
import com.imd.backend.api.dto.similarity.SimilarityResponse;
import com.imd.backend.app.service.bookYard.BookSimilarityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book-similarity")
public class BookSimilarityController extends BaseSimilarityController<SimilarityResponse> {

    public BookSimilarityController(BookSimilarityService service) {
        super(service);
    }
}
