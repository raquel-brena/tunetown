package com.imd.backend.app.service.core;

import com.imd.backend.app.dto.core.CreateBasePostDTO;
import com.imd.backend.app.dto.core.UpdateBasePostDTO;
import com.imd.backend.app.gateway.core.IPostItemGateway;
import com.imd.backend.app.service.UserService;
import com.imd.backend.domain.entities.core.BasePost;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.core.BaseTimelineItem;
import com.imd.backend.domain.valueObjects.core.BaseTrendingItem;
import com.imd.backend.domain.valueObjects.core.PostItem;
import com.imd.backend.infra.persistence.jpa.repository.core.BasePostRepository;

import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public abstract class BasePostService<
    T extends BasePost, // POST que será utilizado
    I extends PostItem, // POST ITEM (sobre o que será postado)
    C extends CreateBasePostDTO,
    U extends UpdateBasePostDTO
> {
    protected final BasePostRepository<T, I> repository;
    protected final UserService userService;
    protected final IPostItemGateway<I> itemGateway;

    protected BasePostService(
        BasePostRepository<T, I> repository, 
        UserService userService,
        IPostItemGateway<I> itemGateway
    ) {
        this.repository = repository;
        this.userService = userService;
        this.itemGateway = itemGateway;
    }

    // --- MÉTODOS FIXOS (CRUD Padrão) ---
    @Transactional
    public T create(String userId, C dto) {
        final User author = userService.findUserById(UUID.fromString(userId))
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        final I item = this.itemGateway.getItemById(dto.getItemId(), dto.getItemType());

        final T entity = createEntityInstance(author, dto, item);

        // 4. Validação de Domínio (Template Method)
        entity.validateState(); // Validações do Pai (BasePost)
        validateSpecificEntity(entity); // Validações do Filho (Tuneet)

        // 5. Salva
        return repository.save(entity);
    }    

    @Transactional
    public T update(UUID id, String userId, U dto) {
        final T post = findById(id);

        if (!post.isOwnedBy(userId)) {
            throw new AccessDeniedException("Você não tem permissão para editar este post.");
        }

        I newItem = null;
        boolean hasItemId = dto.getItemId() != null && !dto.getItemId().isBlank();
        boolean hasItemType = dto.getItemType() != null; // Assume String no DTO Base

        if (hasItemId ^ hasItemType) {
            throw new BusinessException("Para atualizar o item, é necessário passar ID e Tipo.");
        }
        if (hasItemId) {
            newItem = this.itemGateway.getItemById(dto.getItemId(), dto.getItemType());
        }

        // 3. Atualiza a Entidade (Passando DTO completo!)
        updateEntityInstance(post, dto, newItem);

        // 4. Validação de Domínio (Pós-atualização)
        post.validateState(); // Valida estado geral
        validateSpecificEntity(post); // Valida estado específico

        return repository.save(post);
    }    

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<T> findByAuthorId(UUID authorId, Pageable pageable) {
        return repository.findByAuthorId(authorId.toString(), pageable);
    }

    public T findById(UUID id) {
        return repository.findById(id.toString())
                .orElseThrow(() -> new NotFoundException("Post não encontrado com ID: " + id));
    }

    @Transactional
    public void deleteById(UUID id, UUID userId) {
        T post = findById(id);

        // Validação de Segurança (Ponto Fixo)
        if (!post.isOwnedBy(userId.toString())) {
            throw new AccessDeniedException("Você não tem permissão para deletar este post.");
        }

        repository.delete(post);
    }    

    // -------------------------- POST ITENS -------------------------------------
    public List<I> searchItems(String query, String itemType) {
        return itemGateway.searchItem(query, itemType);
    }    
    
    public Page<T> findByItemId(String itemId, Pageable pageable) {
        return repository.findByItemId(itemId, pageable);
    }

    public Page<T> findByItemTitle(String title, Pageable pageable) {
        return repository.findByItemTitle(title, pageable);
    }

    public Page<T> findByItemCreator(String creatorName, Pageable pageable) {
        return repository.findByItemCreator(creatorName, pageable);
    }    
    
    // TRENDING
    public List<BaseTrendingItem<I>> getTrending(String filterType, int limit) {
        // Cria paginação para o limite
        Pageable pageable = PageRequest.of(0, limit);

        return repository.findTrendingItems(filterType, pageable);
    }  

    // RESUMES
    public Page<BaseResume<I>> findAllResume(Pageable pageable) {
        // 1. Busca os dados brutos (já montados como objetos)
        Page<BaseResume<I>> page = repository.findAllResumes(pageable);
        
        // 2. Chama o HOOK para processamento adicional (ex: assinar URL)
        page.forEach(this::postProcessResume);
        
        return page;
    }    

    public Page<BaseResume<I>> findResumeByAuthorId(UUID authorId, Pageable pageable) {
        Page<BaseResume<I>> page = repository.findResumesByAuthorId(authorId.toString(), pageable);

        page.forEach(this::postProcessResume);

        return page;
    }

    public BaseResume<I> findResumeById(UUID id) {
        BaseResume<I> resume = repository.findResumeById(id.toString())
                .orElseThrow(() -> new NotFoundException("Resume não encontrado para o ID: " + id));

        postProcessResume(resume);

        return resume;
    }    

    // TIMELINE
    public Page<BaseTimelineItem<I>> getGlobalTimeLine(Pageable pageable) {
        // Regra Fixa: Timelines são sempre ordenadas por criação decrescente
        Pageable sortedPageable = enforceTimelineSort(pageable);
        return this.repository.findGlobalTimelineItems(sortedPageable);
    }

    public Page<BaseTimelineItem<I>> getHomeTimeLine(UUID userId, Pageable pageable) {
        Pageable sortedPageable = enforceTimelineSort(pageable);
        return this.repository.findHomeTimelineItems(userId.toString(), sortedPageable);
    }

    // --- Helper Privado ---
    private Pageable enforceTimelineSort(Pageable pageable) {
        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));
    }    

    protected void postProcessResume(BaseResume<I> resume) {
        // Default: faz nada
    }
    
    /**
     * A subclasse deve saber como instanciar sua entidade (ex: new Tuneet(...)).
     */
    protected abstract T createEntityInstance(User author, C createDto, I item); 
    
    /**
     * Atualiza a entidade existente com dados do DTO e o novo item (se houver).
     */
    protected abstract void updateEntityInstance(T entity, U dto, I newItem);

    protected abstract void validateSpecificEntity(T entity); 
}
