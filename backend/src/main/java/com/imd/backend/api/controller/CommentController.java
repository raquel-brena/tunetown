package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BaseCommentController;
import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.CommentService;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
public class CommentController extends BaseCommentController<Comment, Tuneet, TunableItem> {

    public CommentController(CommentService commentService) {
        super(commentService);
    }

    @Override
    protected Comment buildComment(CommentDTO dto) {
        return Comment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())
                .build();
    }
}
