package com.imd.backend.app.service.core;

import com.imd.backend.api.dto.comment.CommentDTO;
import com.imd.backend.domain.entities.core.BaseComment;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.valueobjects.core.PostItem;
import com.imd.backend.infra.persistence.jpa.repository.ProfileRepository;
import com.imd.backend.infra.persistence.jpa.repository.core.BaseCommentRepository;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class BaseCommentService <
        T extends BaseComment, // Comment que será utilizado
        P extends BasePost,
        I extends PostItem// LIKE que será utizado
        > {

    protected final BaseCommentRepository<T> repository;
    protected final BaseBotResponder<P, ?> botResponder;
    protected final ProfileRepository profileRepository;
    protected final BasePostRepository<P, I> postRepository;

    protected BaseCommentService(BaseCommentRepository<T> repository, BaseBotResponder<P, ?> botResponder, ProfileRepository profileRepository, BasePostRepository<P, I> postRepository) {
        this.repository = repository;
        this.botResponder = botResponder;
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
    }

    public T create(CommentDTO dto) {

        P post = postRepository.findById(dto.getTuneetId())
                .orElseThrow(() -> new NotFoundException("Post não encontrado."));

        Profile author = profileRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Autor não encontrado."));

        T entity = buildComment(dto, author, post);

        entity.validateState();

        T savedComment = repository.save(entity);

        if (botResponder.isMentioned(dto.getContentText())) {

            String tunableSummary;
            try {
                tunableSummary = post.getContent();
            } catch (Exception ex) {
                tunableSummary = null;
            }

            botResponder.generateResponseAsync(
                    post,
                    tunableSummary,
                    dto.getContentText()
            );
        }

        return savedComment;
    }

    public T update(T comment) {
        T entity = repository.findById(comment.getId())
                .orElseThrow(() -> new NotFoundException("Comentário não encontrado."));
        T saved = repository.save(entity);
        return saved;
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Comentário não encontrado.");
        }
        repository.deleteById(id);
    }

    public Page<T> findByTuneetId(String tuneetId, Pageable pageable) {
        Page<T> entities = repository.findByPostId(tuneetId, pageable);
        if (!entities.hasContent()) {
            throw new NotFoundException("Nenhum comentário encontrado para este tuneet.");
        }
        return entities;
    }

    protected abstract T buildComment(
            CommentDTO dto,
            Profile author,
            P post
    );

}
