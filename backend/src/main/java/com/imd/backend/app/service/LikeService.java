package com.imd.backend.app.service;

import com.imd.backend.app.dto.core.CreateBaseLikeDTO;
import com.imd.backend.app.service.core.BaseLikeService;
import com.imd.backend.domain.entities.tunetown.Like;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.infra.persistence.jpa.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends BaseLikeService<Like,Tuneet, CreateBaseLikeDTO> {

    public LikeService(LikeRepository repository) {
        super(repository);
    }
}
