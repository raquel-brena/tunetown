package com.imd.backend.api.controller;

import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.api.dto.like.LikeResponseDTO;
import com.imd.backend.app.service.LikeService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.mapper.LikeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/likes")
public class LikeController implements CrudController<Long, LikeCreateDTO> {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<RestResponseMessage>> findAll(Pageable pageable) {
        Page<Like> likes = likeService.findAll(pageable);
        if (!likes.hasContent()) {
            throw new NotFoundException("Nenhum like encontrado.");
        }

        Page<RestResponseMessage> response = likes.map(l ->
                new RestResponseMessage(LikeMapper.toDTO(l), HttpStatus.OK.value())
        );

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestResponseMessage> findById(@PathVariable Long id) {
        Like like = likeService.findById(id);
        return ResponseEntity.ok(new RestResponseMessage(LikeMapper.toDTO(like), HttpStatus.OK.value()));
    }

    @Override
    @PostMapping
    public ResponseEntity<RestResponseMessage> create(@Valid @RequestBody LikeCreateDTO dto) {
        Tuneet tuneetLiked = new Tuneet();
        Profile author = new Profile();

        author.setId(dto.getProfileId());
        tuneetLiked.setId(dto.getTuneetId());

        Like created = likeService.create(Like.create(tuneetLiked, author));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponseMessage(LikeMapper.toDTO(created), HttpStatus.CREATED.value(), "Like criado"));
    }

    @Override
    @PutMapping
    public ResponseEntity<RestResponseMessage> update(@Valid @RequestBody LikeCreateDTO dto) {
        Like updated = likeService.update(dto);
        return ResponseEntity.ok(new RestResponseMessage(LikeMapper.toDTO(updated), HttpStatus.OK.value(), "Like atualizado"));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        likeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tuneet/{tuneetId}")
    public ResponseEntity<Page<LikeResponseDTO>> findByTuneetId(@PathVariable String tuneetId, Pageable pageable) {
        Page<Like> likes = likeService.findByTuneetId(tuneetId, pageable);
        Page<LikeResponseDTO> dtoPage = likes.map(LikeMapper::toDTO);

        if (!dtoPage.hasContent()) {
            throw new NotFoundException("Nenhum like encontrado para este tuneet.");
        }

        return ResponseEntity.ok(dtoPage);
    }
}
