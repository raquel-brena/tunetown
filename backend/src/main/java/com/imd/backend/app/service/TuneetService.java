package com.imd.backend.app.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.infra.persistence.jpa.repository.TuneetRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.imd.backend.app.dto.CreateTuneetDTO;
import com.imd.backend.app.dto.UpdateTuneetDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.core.BaseResume;

@Service
public class TuneetService extends BasePostService<
        Tuneet, 
        TunableItem,
        CreateTuneetDTO,
        UpdateTuneetDTO
> {
        private final FileService fileService;

        public TuneetService(
                @Qualifier("TuneetJpaRepository") TuneetRepository tuneetRepository,
                UserService userService,
                @Qualifier("SpotifyGateway") TunablePlataformGateway plataformGateway,
                FileService fileService
        ) {
                super(tuneetRepository, userService, plataformGateway);
                this.fileService = fileService;
        } 

        @Override
        protected void postProcessResume(BaseResume<TunableItem> resume) {
                if (resume.getAuthorPhotoFileName() != null && !resume.getAuthorPhotoFileName().isBlank()) {
                        String presignedUrl = fileService.applyPresignedUrl(resume.getAuthorPhotoFileName());
                        resume.setAuthorPhotoUrl(presignedUrl);
                }
        }
        
        @Override
        protected Tuneet createEntityInstance(User author, CreateTuneetDTO dto, TunableItem item) {
                // Usa o builder aproveitando o DTO tipado
                return Tuneet.builder()
                        .id(UUID.randomUUID().toString())
                        .author(author)
                        .textContent(dto.getTextContent())
                        .createdAt(LocalDateTime.now())
                        
                        // Mapeia Item -> Colunas
                        .tunableItemId(item.getId())
                        .tunableItemPlataform(item.getPlatformName())
                        .tunableItemTitle(item.getTitle())
                        .tunableItemArtist(item.getArtist())
                        .tunableItemType(item.getItemType().toString())
                        .tunableItemArtworkUrl(item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null)
                        
                        // Se o CreateTuneetDTO tivesse campos extras (ex: mood), setaria aqui:
                        // .mood(dto.getMood()) 
                        .build();
        }        

        @Override
        protected void updateEntityInstance(Tuneet entity, UpdateTuneetDTO dto, TunableItem newItem) {
                // Atualiza texto
                if (dto.getTextContent() != null && !dto.getTextContent().isBlank()) {
                        entity.setTextContent(dto.getTextContent());
                }

                // Atualiza item se foi passado
                if (newItem != null) {
                        entity.updateTunableItem(newItem);
                }
        }        

        @Override
        protected void validateSpecificEntity(Tuneet entity) {
                entity.validateTunableItem();
        }        
}
