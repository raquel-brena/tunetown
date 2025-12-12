package com.imd.backend.api.controller.filmlog;

import com.imd.backend.api.controller.core.BaseCommentController;
import com.imd.backend.app.dto.movie.CreateMovieCommentDTO;
import com.imd.backend.app.service.filmlog.MovieCommentService;
import com.imd.backend.domain.entities.filmlog.MovieComment;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/filmlog/comments")
public class MovieCommentController extends BaseCommentController<
    MovieComment, 
    MovieReview, 
    MovieItem,
        CreateMovieCommentDTO
> {

    public MovieCommentController(MovieCommentService commentService) {
        super(commentService);
    }

    @Override
    protected MovieComment buildComment(CreateMovieCommentDTO dto) {

        MovieReview review = null;

        if (dto.getPostId() != null) {
            review = MovieReview.builder()
                    .id(dto.getPostId())
                    .build();
        }

        return MovieComment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())
                .minuteMark(dto.getMinuteMark())
                .post(review)
                .build();
    }

}
