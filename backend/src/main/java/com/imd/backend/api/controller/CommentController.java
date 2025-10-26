package com.imd.backend.api.controller;

import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.api.dto.comment.CommentResponseDTO;
import com.imd.backend.api.dto.comment.CommentUpdateDTO;
import com.imd.backend.app.service.CommentService;
import com.imd.backend.domain.entities.Comment;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.mapper.CommentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Page<RestResponseMessage>> findAll(Pageable pageable) {
        Page<Comment> comments = commentService.findAll(pageable);
        if (!comments.hasContent()) {
            throw new NotFoundException("Nenhum comentário encontrado.");
        }

        Page<RestResponseMessage> response = comments.map(c ->
                new RestResponseMessage(CommentMapper.toDTO(c), HttpStatus.OK.value())
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponseMessage> findById(@PathVariable Long id) {
        Comment comment = commentService.findById(id);
        return ResponseEntity.ok(
                new RestResponseMessage(CommentMapper.toDTO(comment), HttpStatus.OK.value())
        );
    }

    @PostMapping
    public ResponseEntity<RestResponseMessage> create(@Valid @RequestBody CommentCreateDTO dto) {
        Comment created = commentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponseMessage(CommentMapper.toDTO(created), HttpStatus.CREATED.value(), "Comentário criado"));
    }

    @PutMapping
    public ResponseEntity<RestResponseMessage> update(@Valid @RequestBody CommentUpdateDTO dto) {
        Comment updated = commentService.update(CommentMapper.toDomain(dto));
        return ResponseEntity.ok(
                new RestResponseMessage(CommentMapper.toDTO(updated), HttpStatus.OK.value(), "Comentário atualizado")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tuneet/{tuneetId}")
    public ResponseEntity<Page<CommentResponseDTO>> findByTuneetId(
            @PathVariable Long tuneetId,
            Pageable pageable
    ) {
        Page<Comment> comments = commentService.findByTuneetId(tuneetId, pageable);
        Page<CommentResponseDTO> dtoPage = comments.map(CommentMapper::toDTO);

        if (!dtoPage.hasContent()) {
            throw new NotFoundException("Nenhum comentário encontrado para este tuneet.");
        }

        return ResponseEntity.ok(dtoPage);
    }
}
