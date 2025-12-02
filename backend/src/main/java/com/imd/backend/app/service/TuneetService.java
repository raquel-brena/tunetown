package com.imd.backend.app.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.infra.persistence.jpa.mapper.TuneetJpaMapper;
import com.imd.backend.infra.persistence.jpa.projections.TrendingTuneProjection;
import com.imd.backend.infra.persistence.jpa.projections.TuneetResumeProjection;
import com.imd.backend.infra.persistence.jpa.repository.TuneetRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.exception.BusinessException;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.domain.valueObjects.TimeLineItem;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TuneetResume;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

import jakarta.transaction.Transactional;

@Service
public class TuneetService extends BasePostService<Tuneet, TunableItem> {
        private final TuneetRepository tuneetRepository;
        private final TunablePlataformGateway plataformGateway;
        private final FileService fileService;

        public TuneetService(
                @Qualifier("TuneetJpaRepository") TuneetRepository tuneetRepository,
                UserService userService,
                @Qualifier("SpotifyGateway") TunablePlataformGateway plataformGateway,
                FileService fileService
        ) {
                super(tuneetRepository, userService);
                this.tuneetRepository = tuneetRepository;
                this.plataformGateway = plataformGateway;
                this.fileService = fileService;
        }

        public Page<Tuneet> findTuneetByTunableItem(String tunableItemId, Pageable pagination) {
                return this.tuneetRepository.findByTunableItemId(tunableItemId, pagination);
        }        

        public Page<Tuneet> findTuneetByTunableItemTitleContaining(String word, Pageable pagination) {
                return this.tuneetRepository.findByTunableItemTitleContainingIgnoreCase(word, pagination);
        }        

        public Page<Tuneet> findTuneetByTunableItemArtistContaining(String word, Pageable pagination) {
                return this.tuneetRepository.findByTunableItemArtistContainingIgnoreCase(word, pagination);
        }  

        public List<TunableItem> searchTunableItems(String query, TunableItemType itemType) {
                return this.plataformGateway.searchItem(query, itemType);
        }

        public List<TrendingTuneResult> getTrendingTunes(TunableItemType type, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        Page<TrendingTuneProjection> result =
                tuneetRepository.findTrendingTunesByTunableItemType(type.toString(), pageable);

        return result.getContent().stream()
                .map(p -> new TrendingTuneResult(
                        p.getItemId(),
                        p.getTitle(),
                        p.getArtist(),
                        p.getPlatformId(),
                        TunableItemType.fromString(p.getItemType()),
                        p.getArtworkUrl() != null ? URI.create(p.getArtworkUrl()) : null,
                        p.getTuneetCount()
                ))
                .toList();
        }


        public Page<TuneetResume> findAllTuneetResume(Pageable pagination) {
                final var projection = tuneetRepository.findAllTuneetResume(pagination);
                return projection.map(
                        p -> TuneetJpaMapper.resumeFromProjection(p)
                );
        }

        public Page<TuneetResume> findTuneetResumeByAuthorId(UUID authorId, Pageable pagination) {

                final Page<TuneetResumeProjection> projection =
                        this.tuneetRepository.findTuneetResumeByAuthorId(authorId.toString(), pagination);
                
                return projection.map(
                        p -> TuneetJpaMapper.resumeFromProjection(p)
                ); 
        }

        public TuneetResume findTuneetResumeById(UUID id) {
                final Optional<TuneetResumeProjection> op = this.tuneetRepository.findTuneetResumeById(String.valueOf(id));

                if(op.isEmpty()) throw new NotFoundException("Nenhum tuneet foi encontrado com esse ID");
                
                final TuneetResumeProjection tuneetResume = op.get();

                TuneetResume resume = TuneetJpaMapper.resumeFromProjection(tuneetResume);

                if (tuneetResume.getFileNamePhoto() != null) {
                final String presignedUrl = this.fileService.applyPresignedUrl(tuneetResume.getFileNamePhoto());
                        resume.setUrlPhoto(presignedUrl);
                }


                return resume;
        }

        public Page<TimeLineItem> getGlobalTimeLine(Pageable pagination) {
                Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
                Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);
                final var projection = this.tuneetRepository.findGlobalTimeline(pageable);
                return projection.map(
                        p -> TuneetJpaMapper.fromTimelineProjection(p)
                );
        }

        public Page<TimeLineItem> getHomeTimeLine(UUID userId, Pageable pagination) {
                Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
                Pageable pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort);
                final var projection = this.tuneetRepository.findHomeTimeline(userId.toString(), pageable);
                return projection.map(
                                p -> TuneetJpaMapper.fromTimelineProjection(p)
                );
        }


        @Transactional(rollbackOn = Exception.class)
        public Tuneet deleteById(UUID tuneetId) {
                final Optional<Tuneet> findedTuneet = this.tuneetRepository.findById(tuneetId.toString());

                if(findedTuneet.isEmpty())
                throw new NotFoundException("Não foi encontrado nenhum tuneet com esse ID");
                
                this.tuneetRepository.deleteById(tuneetId.toString());
                return findedTuneet.get();
        }

        @Transactional(rollbackOn = Exception.class)
        public Tuneet updateTuneet(
        UUID tuneetId,
        String textContent,
        String tunableItemId,
        TunableItemType tunableItemType
        ) {
                final Tuneet tuneet = this.findById(tuneetId); // Usa método do pai
                
                final boolean hasTextContent = textContent != null && !textContent.isBlank();
                final boolean hasItemId = tunableItemId != null && !tunableItemId.isEmpty();
                final boolean hasItemType = tunableItemType != null;

                // Se tiver o tipo e o ID do item, mas não ambos juntos
                if(hasItemId ^ hasItemType) 
                throw new BusinessException("Para atualizar o item, é necessário passar o ID e o tipo dele");

                if(hasTextContent)
                tuneet.setTextContent(textContent);
                
                this.tuneetRepository.save(tuneet);
                return tuneet;
        }

        @Override
        protected TunableItem resolveItem(String itemId, String itemType) {
                TunableItemType type = TunableItemType.fromString(itemType);
                return plataformGateway.getItemById(itemId, type);        
        }

        @Override
        protected Tuneet createEntityInstance(User author, String textContent, TunableItem item) {
                // Usa o Factory Method da Entidade Rica
                return Tuneet.create(author, textContent, item);
        }
}
