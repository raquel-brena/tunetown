package com.imd.backend.api.controller;

import com.imd.backend.api.dto.RestResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CrudController<ID, Entity> {
    ResponseEntity<Page<RestResponseMessage>> findAll(Pageable pageable);

    ResponseEntity<RestResponseMessage> findById(ID id);

    ResponseEntity<RestResponseMessage> create(Entity entity);

    ResponseEntity<RestResponseMessage> update(Entity entity);

    ResponseEntity<Void> delete(ID id);
}
