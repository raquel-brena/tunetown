package com.imd.backend.api.controller.core;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.core.PostItem;

public abstract class BasePostController<T extends BasePost, I extends PostItem> {
  protected final BasePostService<T, I> service;

  protected BasePostController(BasePostService<T, I> service) {
      this.service = service;
  }  

  // --- PONTOS FIXOS (Endpoints Padrão) ---
  @GetMapping
  public ResponseEntity<Page<T>> getAll(
          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ) {
      return ResponseEntity.ok(service.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<T> getById(@PathVariable UUID id) {
      return ResponseEntity.ok(service.findById(id));
  }

  @GetMapping("/author/{authorId}")
  public ResponseEntity<Page<T>> getByAuthor(
          @PathVariable UUID authorId,
          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ) {
      return ResponseEntity.ok(service.findByAuthorId(authorId, pageable));
  }
}
