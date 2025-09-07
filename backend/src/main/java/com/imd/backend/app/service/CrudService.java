package com.imd.backend.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface CrudService<ID, Entity> {
    Page<Entity> findAll(Pageable pageable);
    Entity findById(ID id);
    Entity create(Entity entity);
    Entity update(Entity entity);
    void delete(ID id);
}
