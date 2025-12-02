package com.imd.backend.app.service;

import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.repository.LikeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository repository;

    public LikeService(LikeRepository repository) {
        this.repository = repository;
    }

    public Page<Like> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Like findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Like não encontrado."));
    }

    public Like create(Like like) {
        Optional<Like> existLike = repository.findByProfileIdAndTuneetId(like.getProfile().getId(), like.getTuneet().getId());

        if (existLike.isPresent()) {
            Like likeEntity = existLike.get();
            repository.delete(likeEntity);
            return null;
        } else {
            return repository.save(like);
        }
    }

    public Like update(LikeCreateDTO like) {
        Like entity = repository.findById(like.getId())
                .orElseThrow(() -> new NotFoundException("Like não encontrado para atualização."));

        Tuneet tuneet = new Tuneet();
        tuneet.setId(like.getTuneetId());

        entity.setTuneet(tuneet);

        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Like não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }

    public Page<Like> findByTuneetId(String tuneetId, Pageable pageable) {
        Page<Like> entities = repository.findByTuneetId(tuneetId, pageable);
        if (!entities.hasContent()) {
            throw new NotFoundException("Nenhum like encontrado para este tuneet.");
        }
        return entities;
    }
}
