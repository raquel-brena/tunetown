package com.imd.backend.app.service.core;

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
    I extends PostItem // POST ITEM (sobre o que será postado)
> {

    protected final BasePostRepository<T, I> repository;
    protected final UserService userService;

    protected BasePostService(BasePostRepository<T, I> repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    // --- MÉTODOS FIXOS (CRUD Padrão) ---

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

    /**
     * TEMPLATE METHOD: O algoritmo de criação.
     * O Framework define O FLUXO, a Aplicação define OS DETALHES.
     */
    @Transactional
    public T create(String userId, String textContent, String itemId, String itemType) {
        // 1. Valida Usuário (Fixo)
        final User author = userService.findUserById(UUID.fromString(userId))
             .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        // 2. Busca o Item na API Externa (Variável - Abstract)
        final I item = resolveItem(itemId, itemType);

        // 3. Instancia a Entidade Concreta (Variável - Abstract)
        final T entity = createEntityInstance(author, textContent, item);

        // 4. Salva (Fixo)
        return repository.save(entity);
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
     * A subclasse deve saber como buscar o item (ex: chamar SpotifyGateway).
     */
    protected abstract I resolveItem(String itemId, String itemType); 
    
    /**
     * A subclasse deve saber como instanciar sua entidade (ex: new Tuneet(...)).
     */
    protected abstract T createEntityInstance(User author, String textContent, I item);    
}
