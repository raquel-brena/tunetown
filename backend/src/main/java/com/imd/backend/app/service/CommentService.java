package com.imd.backend.app.service;

import com.imd.backend.app.dto.core.CreateBaseCommentDTO;
import com.imd.backend.app.service.core.BaseCommentService;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;
import com.imd.backend.infra.persistence.jpa.repository.CommentRepository;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.TuneetRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseCommentService<Comment, Tuneet, TunableItem, CreateBaseCommentDTO>{

    public CommentService(CommentRepository repository,
                             TutoResponderService botResponder,
                             ProfileRepository profileRepository,
                             TuneetRepository tuneetRepository) {
        super(repository, botResponder, profileRepository, tuneetRepository);
    }

    @Override
    protected Comment buildComment(CreateBaseCommentDTO dto, Profile author, Tuneet post) {
        return null;
    }
}
