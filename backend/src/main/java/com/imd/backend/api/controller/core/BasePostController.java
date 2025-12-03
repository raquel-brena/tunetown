package com.imd.backend.api.controller.core;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;
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

  // TRENDING
  
  @GetMapping("/trending")
  public ResponseEntity<List<BaseTrendingItem<I>>> getTrending(
    @RequestParam(required = false) String type,
    @RequestParam(defaultValue = "10") int limit
    ) {
        // Chama o método genérico do serviço
        return ResponseEntity.ok(service.getTrending(type, limit));
    }  
    
    // RESUMES
    @GetMapping("/resume")
    public ResponseEntity<Page<BaseResume<I>>> getAllResumes(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BaseResume<I>> page = service.findAllResume(pageable);
        return ResponseEntity.ok(page); 
    }

    @GetMapping("/author/{authorId}/resume")
    public ResponseEntity<Page<BaseResume<I>>> getResumesByAuthor(
            @PathVariable UUID authorId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BaseResume<I>> page = service.findResumeByAuthorId(authorId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}/resume")
    public ResponseEntity<BaseResume<I>> getResumeById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findResumeById(id));
    }    
}
