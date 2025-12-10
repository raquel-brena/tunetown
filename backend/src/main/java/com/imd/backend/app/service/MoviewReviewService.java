package com.imd.backend.app.service;

import com.imd.backend.app.dto.CreateTuneetDTO;
import com.imd.backend.app.dto.UpdateTuneetDTO;
import com.imd.backend.app.dto.movie.CreateMovieReviewDTO;
import com.imd.backend.app.dto.movie.UpdateMovieReviewDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.FilmPlatformGateway;
import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.entities.filmLog.MovieReview;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.domain.valueObjects.movieItem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.MovieReviewRepository;
import com.imd.backend.infra.persistence.jpa.repository.TuneetRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MoviewReviewService extends BasePostService<
        MovieReview,
        MovieItem,
        CreateMovieReviewDTO,
        UpdateMovieReviewDTO
> {
        private final FileService fileService;

        public MoviewReviewService(
                @Qualifier("MovieJpaRepository") MovieReviewRepository repository,
                UserService userService,
                @Qualifier("TmdbGateway") FilmPlatformGateway gateway,
                FileService fileService
        ) {
                super(repository, userService, gateway);
                this.fileService = fileService;
        } 

        @Override
        protected void postProcessResume(BaseResume<MovieItem> resume) {
                if (resume.getAuthorPhotoFileName() != null && !resume.getAuthorPhotoFileName().isBlank()) {
                        String presignedUrl = fileService.applyPresignedUrl(resume.getAuthorPhotoFileName());
                        resume.setAuthorPhotoUrl(presignedUrl);
                }
        }
        
        @Override
        protected MovieReview createEntityInstance(User author, CreateMovieReviewDTO dto, MovieItem item) {
                // Usa o builder aproveitando o DTO tipado
                return MovieReview.builder()
                        .id(UUID.randomUUID().toString())
                        .author(author)
                        .textContent(dto.getTextContent())
                        .createdAt(LocalDateTime.now())
//
//                        // Mapeia Item -> Colunas
//                        .tunableItemId(item.getId())
//                        .tunableItemPlataform(item.getPlatformName())
//                        .tunableItemTitle(item.getTitle())
//                        .tunableItemArtist(item.getArtist())
//                        .tunableItemType(item.getItemType().toString())
//                        .tunableItemArtworkUrl(item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null)
                        
                        // Se o CreateTuneetDTO tivesse campos extras (ex: mood), setaria aqui:
                        // .mood(dto.getMood()) 
                        .build();
        }        

        @Override
        protected void updateEntityInstance(MovieReview entity, UpdateMovieReviewDTO dto, MovieItem newItem) {
                // Atualiza texto
                if (dto.getTextContent() != null && !dto.getTextContent().isBlank()) {
                        entity.setTextContent(dto.getTextContent());
                }

                // Atualiza item se foi passado
                if (newItem != null) {
                        entity.updateMovieItem(newItem);
                }
        }        

        @Override
        protected void validateSpecificEntity(MovieReview entity) {
                entity.validateMovieReview();
        }        
}
