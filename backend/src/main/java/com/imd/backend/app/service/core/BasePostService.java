package com.imd.backend.app.service.core;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.app.service.UserService;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.repository.core.BasePostRepository;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public abstract class BasePostService<T,   // Entidade (Tuneet)
        I    // Item vinculável (TunableItem)
        > {

    protected final TunablePlataformGateway platformGateway;
    protected final BasePostRepository<T> repository;
    protected final UserService userService;
    protected final TuneetJpaMapper mapper;

    protected BasePostService(TunablePlataformGateway platformGateway, BasePostRepository<T> repository, UserService userService, TuneetJpaMapper mapper) {
        this.platformGateway = platformGateway;
        this.repository = repository;
        this.userService = userService;
        this.mapper = mapper;
    }

    protected abstract T saveEntity(T entity);

    protected abstract I fetchItemById(String itemId, Object itemType);

    protected abstract void applyNewItem(T entity, I item);

    protected abstract void applyTextUpdate(T entity, String text);

    protected abstract T createNewEntity(UUID userId, String text, I item);


    public Optional<T> findById(String id) {
        return this.repository.findById(id);
    }

    public PageResult<T> list(Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));
        final Page<T> postsList = this.repository.findAll(pageable);

        return new PageResult<>(postsList.getContent().stream().toList(),

                postsList.getNumberOfElements(), postsList.getTotalElements(), postsList.getNumber(), postsList.getSize(), postsList.getTotalPages());
    }

    public PageResult<T> listByAuthorId(String authorId, Pagination pagination) {
        final Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

        Page<T> postList = repository.findByAuthorId(authorId, pageable);

        return new PageResult<>(postList.getContent().stream().toList(), postList.getNumberOfElements(), postList.getTotalElements(), postList.getNumber(), postList.getSize(), postList.getTotalPages());
    }

    public PageResult<T> listByItem(String itemId, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));

        Page<T> page = this.repository.findByTunableItemId(itemId, pageable);
        return new PageResult<>(page.getContent(), page.getNumberOfElements(), page.getTotalElements(), page.getNumber(), page.getSize(), page.getTotalPages());
    }

    public PageResult<T> findTuneetByTunableItemTitleContaining(String word, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.page(), pagination.size(), Sort.by(Sort.Direction.fromString(pagination.orderDirection()), pagination.orderBy()));
        Page<T> page = this.repository.findByTunableItemTitleContainingIgnoreCase(word, pageable);
        return new PageResult<>(page.getContent(), page.getNumberOfElements(), page.getTotalElements(), page.getNumber(), page.getSize(), page.getTotalPages());
    }


    @Transactional
    public T delete(UUID id) {
        Optional<T> post = findById(id.toString());
        if (post.isEmpty()) {
            throw new RuntimeException("Nenhum post encontrado com o ID informado.");
        }

        this.repository.deleteById(post.toString());
        return post.get();
    }

    @Transactional
    public T update(UUID entityId, String textContent, String itemId, Object itemType) {

        T entity = findById(entityId.toString()).orElseThrow(() -> new RuntimeException("Nenhum registro encontrado com esse ID"));

        boolean hasText = textContent != null && !textContent.isBlank();
        boolean hasItemId = itemId != null && !itemId.isBlank();
        boolean hasType = itemType != null;

        if (hasItemId ^ hasType) {
            throw new RuntimeException("Para atualizar o item, é necessário enviar ID + tipo.");
        }

        if (hasItemId && hasType) {
            I item = fetchItemById(itemId, itemType);
            applyNewItem(entity, item);
        }

        if (hasText) {
            applyTextUpdate(entity, textContent);
        }

        this.repository.save(entity);
        return entity;
    }

    @Transactional
    public T create(UUID userId, String itemId, Object itemType, String textContent) {

        I item = fetchItemById(itemId, itemType);

        T entity = createNewEntity(userId, textContent, item);

        return saveEntity(entity);
    }
}
