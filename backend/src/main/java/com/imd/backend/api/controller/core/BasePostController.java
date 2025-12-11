package com.imd.backend.api.controller.core;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imd.backend.app.dto.core.CreateBasePostDTO;
import com.imd.backend.app.dto.core.UpdateBasePostDTO;
import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;
import com.imd.backend.domain.valueObjects.core.PostItem;
import com.imd.backend.infra.security.CoreUserDetails;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public abstract class BasePostController<
    T extends BasePost,
    I extends PostItem,
    C extends CreateBasePostDTO,
    U extends UpdateBasePostDTO
> {
  protected final BasePostService<T, I, C, U> service;

  protected BasePostController(BasePostService<T, I, C, U> service) {
      this.service = service;
  }  

  // ------------------------------- ENDPOINTS PADRÃO ------------------------------
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<T> create(
    @Validated @RequestBody C dto,
    @AuthenticationPrincipal CoreUserDetails userDetails
  ) {
    System.out.println("DTO Recebido: " + dto.toString());
    final T created = service.create(userDetails.getUserId(), dto);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<T> update(
    @PathVariable UUID id,
    @Validated @RequestBody U dto,
    @AuthenticationPrincipal CoreUserDetails userDetails 
  ) {
    final T updated = service.update(id, userDetails.getUserId(), dto);
    return ResponseEntity.ok(updated);
  }
  
  @GetMapping
  public ResponseEntity<Page<T>> getAll(
          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
      return ResponseEntity.ok(service.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<T> getById(@PathVariable UUID id) {
      return ResponseEntity.ok(service.findById(id));
  }

  @GetMapping("/author/{authorId}")
  public ResponseEntity<Page<T>> getByAuthor(
          @PathVariable UUID authorId,
          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
      return ResponseEntity.ok(service.findByAuthorId(authorId, pageable));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(
          @PathVariable UUID id,
          @AuthenticationPrincipal CoreUserDetails userDetails // Tipo Concreto!
  ) {
      if (userDetails == null)
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

      UUID userId = UUID.fromString(userDetails.getUserId());

      service.deleteById(id, userId);
      return ResponseEntity.noContent().build();
  }
  
  // ------------------------------- POST ITENS ---------------------------------------
  @GetMapping("/search-item")
  public ResponseEntity<List<I>> searchTunableItem(
          @RequestParam String query,
          @RequestParam(defaultValue = "MUSIC", name = "type") String itemType) {
      final List<I> items = this.service.searchItems(query, itemType);
      return ResponseEntity.ok(items);
  }

  @GetMapping(path = "item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<T>> getPostsByItem(
          @PathVariable String itemId,
          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
      return ResponseEntity.ok(this.service.findByItemId(itemId, pageable));
  }

  @GetMapping(path = "item/search-by-title/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<T>> getPostsByItemTitleContaining(
          @PathVariable String word,
          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
      return ResponseEntity.ok(this.service.findByItemTitle(word, pageable));
  }

  @GetMapping(path = "item/search-by-creator/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<T>> getPostsByItemCreator(
          @PathVariable String word,
          @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
      return ResponseEntity.ok(this.service.findByItemCreator(word, pageable));
  }  

  // ------------------------------- TRENDING TOPICS ------------------------------
  
  @GetMapping("/trending")
  public ResponseEntity<List<BaseTrendingItem<I>>> getTrending(
    @RequestParam(required = false) String type,
    @RequestParam(defaultValue = "10") int limit
    ) {
        // Chama o método genérico do serviço
        return ResponseEntity.ok(service.getTrending(type, limit));
    }  
    
    // -------------------------------  RESUMES -------------------------------
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

    // ------------------------------- TIMELINE -------------------------------
    @GetMapping("/global")
    public ResponseEntity<Page<BaseTimelineItem<I>>> getGlobalTimeline(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(service.getGlobalTimeLine(pageable));
    }

    @GetMapping("/home")
    public ResponseEntity<Page<BaseTimelineItem<I>>> getHomeTimeline(
            @PageableDefault(size = 20) Pageable pageable,
            @AuthenticationPrincipal CoreUserDetails userDetails // O Spring injeta o tipo U aqui!
    ) {
        // Validação genérica (se o Spring Security falhar ou usuário anônimo)
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        UUID userId = UUID.fromString(userDetails.getUserId());

        return ResponseEntity.ok(service.getHomeTimeLine(userId, pageable));
    }
}
