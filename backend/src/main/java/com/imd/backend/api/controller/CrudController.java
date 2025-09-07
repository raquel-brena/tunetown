package com.imd.backend.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CrudController<ID, Entity> {

    ResponseEntity<Page<Entity>> findAll(Pageable pageable);

    ResponseEntity<Entity> findById(ID id);

    ResponseEntity<Entity> create(Entity entity);

    ResponseEntity<Entity> update(Entity entity);

    ResponseEntity<Void> delete(ID id);
}
