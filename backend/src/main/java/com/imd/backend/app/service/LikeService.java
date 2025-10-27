package com.imd.backend.app.service;

import com.imd.backend.domain.entities.Like;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.entity.LikeEntity;
import com.imd.backend.infra.persistence.jpa.mapper.LikeMapper;
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
        return repository.findAll(pageable).map(LikeMapper::toDomain);
    }

    public Like findById(Long id) {
        LikeEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Like não encontrado."));
        return LikeMapper.toDomain(entity);
    }

    public Like create(Like like) {
        LikeEntity entity = LikeMapper.toEntity(like);
        Optional<LikeEntity> existLike = repository.findByProfileIdAndTuneetId(like.getProfileId(), like.getTuneetId());

        System.out.println(like.getProfileId() + "\t" + like.getTuneetId());

        if (existLike.isPresent()) {
            LikeEntity likeEntity = existLike.get();
            repository.delete(likeEntity);
            return null;
        } else {
            LikeEntity saved = repository.save(entity);
            return LikeMapper.toDomain(saved);
        }
    }

    public Like update(Like like) {
        LikeEntity entity = repository.findById(like.getId())
                .orElseThrow(() -> new NotFoundException("Like não encontrado para atualização."));
        entity.setTuneet(LikeMapper.toTuneetEntity(like.getTuneetId()));
        entity.setProfile(LikeMapper.toProfileEntity(like.getProfileId()));
        LikeEntity updated = repository.save(entity);
        return LikeMapper.toDomain(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Like não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }

    public Page<Like> findByTuneetId(String tuneetId, Pageable pageable) {
        Page<LikeEntity> entities = repository.findByTuneetId(tuneetId, pageable);
        if (!entities.hasContent()) {
            throw new NotFoundException("Nenhum like encontrado para este tuneet.");
        }
        return entities.map(LikeMapper::toDomain);
    }
}
