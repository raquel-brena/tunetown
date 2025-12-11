package com.imd.backend.api.controller.filmLog;

import com.imd.backend.api.controller.core.BaseCommentController;
import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.filmLog.MovieCommentService;
import com.imd.backend.domain.entities.filmLog.MovieComment;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/filmlog/comments")
public class MovieCommentController extends BaseCommentController<
    MovieComment, 
    MovieReview, 
    MovieItem
> {

    public MovieCommentController(MovieCommentService commentService) {
        super(commentService);
    }

    @Override
    protected MovieComment buildComment(CommentDTO dto) {
        return MovieComment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())
                .build();
    }
}
