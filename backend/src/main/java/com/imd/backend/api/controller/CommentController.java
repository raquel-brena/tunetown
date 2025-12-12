package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BaseCommentController;
import com.imd.backend.app.dto.core.CreateBaseCommentDTO;
import com.imd.backend.app.service.CommentService;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comments")
public class CommentController extends BaseCommentController<Comment, Tuneet, TunableItem, CreateBaseCommentDTO> {

    public CommentController(CommentService commentService) {
        super(commentService);
    }

    @Override
    protected Comment buildComment(CreateBaseCommentDTO dto) {
        return Comment.builder()
                .id(dto.getId())
                .contentText(dto.getContentText())
                .build();
    }
}
