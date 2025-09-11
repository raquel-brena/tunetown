package com.imd.backend.api.controller;

import com.imd.backend.app.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public abstract class AbstractCrudController<ID, Entity, DTO> implements CrudController<ID, DTO> {

    protected final CrudService<ID, Entity> service;
    private final Function<Entity, DTO> toDTO;
    private final Function<DTO, Entity> toEntity;

    protected AbstractCrudController(CrudService<ID, Entity> service,
                                     Function<Entity, DTO> toDTO,
                                     Function<DTO, Entity> toEntity) {
        this.service = service;
        this.toDTO = toDTO;
        this.toEntity = toEntity;
    }

    @Override
    public ResponseEntity<Page<DTO>> findAll(Pageable pageable) {
        Page<Entity> entities = service.findAll(pageable);
        Page<DTO> dtoPage = entities.map(toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @Override
    public ResponseEntity<DTO> findById(ID id) {
        Entity entity = service.findById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDTO.apply(entity));
    }

    @Override
    public ResponseEntity<DTO> create(DTO dto) {
        Entity entity = toEntity.apply(dto);
        Entity created = service.create(entity);
        return ResponseEntity.status(201).body(toDTO.apply(created));
    }

    @Override
    public ResponseEntity<DTO> update(DTO dto) {
        Entity entity = toEntity.apply(dto);
        Entity updated = service.update(entity);
        return ResponseEntity.ok(toDTO.apply(updated));
    }

    @Override
    public ResponseEntity<Void> delete(ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
