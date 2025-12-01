package com.imd.backend.app.service;

import com.imd.backend.api.dto.comment.CommentCreateDTO;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.entities.tunetown.Comment;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.repository.TuneetRepository;
import com.imd.backend.domain.repository.CommentRepository;
import com.imd.backend.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    private final CommentRepository repository;
    @Autowired
    private final TuneetRepository tuneetRepository;
    @Autowired
    private final ProfileRepository profileRepository;
    @Autowired
    private final TutoResponder tutoResponder;

    public Page<Comment> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Comment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
    }

    public Comment create(CommentCreateDTO dto) {
        Tuneet tuneet = tuneetRepository.findById(dto.getTuneetId())
                .orElseThrow();
        Profile author = profileRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Autor não encontrado."));

        Comment entity = Comment.create(author, tuneet, dto.getContentText());
        Comment savedComment = repository.save(entity);

        if (containsTutoMention(dto.getContentText())) {
            String tunableSummary;
            try {
                tunableSummary = String.valueOf(tuneet.getTunableContent());
            } catch (Exception ex) {
                tunableSummary = null;
            }
            tutoResponder.generateResponseAsync(
                    savedComment.getTuneet().getId(),
                    tuneet.getTunableContent(),
                    tunableSummary,
                    dto.getContentText()
            );
        }

        return savedComment;
    }

    public Comment update(Comment comment) {
        Comment entity = repository.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
        Comment saved = repository.save(entity);
        return saved;
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Comentário não encontrado.");
        }
        repository.deleteById(id);
    }

    public Page<Comment> findByTuneetId(String tuneetId, Pageable pageable) {
        Page<Comment> entities = repository.findByTuneetId(tuneetId, pageable);
        if (!entities.hasContent()) {
            throw new NotFoundException("Nenhum comentário encontrado para este tuneet.");
        }
        return entities;
    }

    private boolean containsTutoMention(String content) {
        return content != null && content.toLowerCase().contains("@tuto");
    }
}
