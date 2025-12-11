package com.imd.backend.app.service;

import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.app.service.core.BaseCommentService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.TuneetRepository;

import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseCommentService<Comment, Tuneet, TunableItem>{

    public CommentService(CommentRepository repository,
                             BotResponder botResponder,
                             ProfileRepository profileRepository,
                             TuneetRepository tuneetRepository) {
        super(repository, botResponder, profileRepository, tuneetRepository);
    }

    @Override
    protected Comment buildComment(CommentDTO dto, Profile author, Tuneet post) {
        return null;
    }
}
