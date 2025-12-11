package com.imd.backend.api.controller.core;

import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.api.dto.comment.CommentResponseDTO;
import com.imd.backend.app.service.core.BaseCommentService;
import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.core.PostItem;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.mapper.CommentMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseCommentController<
        T extends BaseComment,
        P extends BasePost,
        I extends PostItem
        > {

    protected final BaseCommentService<T, P, I> service;

    protected BaseCommentController(BaseCommentService<T, P, I> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<RestResponseMessage>> findAll(Pageable pageable) {
        Page<T> comments = service.findAll(pageable);

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
        T comment = service.findById(id);

        return ResponseEntity.ok(
                new RestResponseMessage(CommentMapper.toDTO(comment), HttpStatus.OK.value())
        );
    }

    @PostMapping
    public ResponseEntity<RestResponseMessage> create(@Valid @RequestBody CommentDTO dto) {
        T created = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponseMessage(CommentMapper.toDTO(created),
                        HttpStatus.CREATED.value(), "Comentário criado"));
    }

    @PutMapping
    public ResponseEntity<RestResponseMessage> update(@Valid @RequestBody CommentDTO dto) {

        T entity = buildComment(dto);
        T updated = service.update(entity);

        return ResponseEntity.ok(
                new RestResponseMessage(
                        CommentMapper.toDTO(updated),
                        HttpStatus.OK.value(),
                        "Comentário atualizado"
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tuneet/{tuneetId}")
    public ResponseEntity<Page<CommentResponseDTO>> findByTuneetId(
            @PathVariable String tuneetId,
            Pageable pageable
    ) {
        Page<T> comments = service.findByTuneetId(tuneetId, pageable);
        Page<CommentResponseDTO> dtoPage = comments.map(CommentMapper::toDTO);

        if (!dtoPage.hasContent()) {
            throw new NotFoundException("Nenhum comentário encontrado para este tuneet.");
        }

        return ResponseEntity.ok(dtoPage);
    }

    protected abstract T buildComment(CommentDTO dto);
}
