package com.imd.backend.api.controller.bookyard;

import com.imd.backend.api.controller.core.BaseCommentController;
import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.bookyard.BookCommentService;
import com.imd.backend.domain.entities.bookyard.BookComment;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;

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
