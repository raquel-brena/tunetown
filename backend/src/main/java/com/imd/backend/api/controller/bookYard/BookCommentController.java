package com.imd.backend.api.controller.bookYard;

import com.imd.backend.api.controller.core.BaseCommentController;
import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.bookYard.BookCommentService;
import com.imd.backend.domain.entities.bookYard.BookComment;
import com.imd.backend.domain.entities.bookYard.BookReview;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bookyard/comments")
public class BookCommentController extends BaseCommentController<BookComment, BookReview, BookItem> {

    public BookCommentController(BookCommentService commentService) {
        super(commentService);
    }

    @Override
    protected BookComment buildComment(CommentDTO dto) {
        return BookComment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())
                .build();
    }
}
