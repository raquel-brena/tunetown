package com.imd.backend.app.service.core;

import com.imd.backend.app.dto.core.CreateBaseLikeDTO;
import com.imd.backend.domain.entities.core.BaseLike;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseLikeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class BaseLikeService <
                T extends BaseLike, // POST que será utilizado
                P extends BasePost, // LIKE que será utizado
                D extends CreateBaseLikeDTO
                > {

    protected final BaseLikeRepository<T> repository;

    protected BaseLikeService(BaseLikeRepository<T> repository) {
        this.repository = repository;
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Like não encontrado."));
    }

    public T create(T like) {
        Optional<T> existing = repository
                .findByProfileIdAndPostId(
                        like.getProfile().getId(),
                        like.getPost().getId()
                );

        if (existing.isPresent()) {
            repository.delete(existing.get());
            return null;
        }

        return repository.save(like);
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Like não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }

    public Page<T> findByTuneetId(String tuneetId, Pageable pageable) {
        Page<T> entities = repository.findByPostId(tuneetId, pageable);
        if (!entities.hasContent()) {
            throw new NotFoundException("Nenhum like encontrado para este tuneet.");
        }
        return entities;
    }


}
