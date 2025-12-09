package com.imd.backend.app.service;

import com.imd.backend.api.dto.like.LikeCreateDTO;
import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.repository.LikeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService extends BaseLikeService<Like,Tuneet> {

    private final LikeRepository repository;

    public LikeService(LikeRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
