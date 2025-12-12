package com.imd.backend.api.controller.core;

import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.api.dto.like.LikeResponseDTO;
import com.imd.backend.app.dto.core.CreateBaseLikeDTO;
import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.core.BaseLike;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.mapper.LikeMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseLikeController <
        T extends BaseLike,
        I extends BasePost,
        D extends CreateBaseLikeDTO
        > {

    protected final BaseLikeService<T, I, D> service;

    protected BaseLikeController(BaseLikeService<T, I, D> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<RestResponseMessage>> findAll(Pageable pageable) {
        Page<T> likes = service.findAll(pageable);
        if (!likes.hasContent()) {
            throw new NotFoundException("Nenhum like encontrado.");
        }

        Page<RestResponseMessage> response = likes.map(l ->
                new RestResponseMessage(LikeMapper.toDTO(l), HttpStatus.OK.value())
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponseMessage> findById(@PathVariable Long id) {
        T like = service.findById(id);
        return ResponseEntity.ok(new RestResponseMessage(LikeMapper.toDTO(like), HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<RestResponseMessage> create(
            @Valid @RequestBody D dto
    ) {

        T like = buildLike(dto);

        T created = service.create(like);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponseMessage(
                        LikeMapper.toDTO(created),
                        HttpStatus.CREATED.value(),
                        "Like processado"
                ));
    }

    @PutMapping
    public ResponseEntity<RestResponseMessage> update(@Valid @RequestBody T dto) {
        T updated = service.create(dto);
        return ResponseEntity.ok(new RestResponseMessage(LikeMapper.toDTO(updated), HttpStatus.OK.value(), "Like atualizado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tuneet/{tuneetId}")
    public ResponseEntity<Page<LikeResponseDTO>> findByTuneetId(@PathVariable String tuneetId, Pageable pageable) {
        Page<T> likes = service.findByTuneetId(tuneetId, pageable);
        Page<LikeResponseDTO> dtoPage = likes.map(LikeMapper::toDTO);

        if (!dtoPage.hasContent()) {
            throw new NotFoundException("Nenhum like encontrado para este tuneet.");
        }

        return ResponseEntity.ok(dtoPage);
    }

    protected abstract T buildLike(D dto);

}
