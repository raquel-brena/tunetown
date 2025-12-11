package com.imd.backend.app.service.bookYard;

import com.imd.backend.app.dto.bookYard.CreateBookReviewDTO;
import com.imd.backend.app.dto.bookYard.UpdateBookReviewDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.FilmPlatformGateway;
import com.imd.backend.app.service.FileService;
import com.imd.backend.app.service.UserService;
import com.imd.backend.app.service.core.BasePostService;
import com.imd.backend.domain.entities.bookYard.BookReview;
import com.imd.backend.domain.entities.core.User;
import com.imd.backend.domain.valueObjects.bookItem.BookItem;
import com.imd.backend.domain.valueObjects.core.BaseResume;
import com.imd.backend.infra.persistence.jpa.repository.bookYard.BookReviewRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookReviewService extends BasePostService<
        BookReview,
        BookItem,
        CreateBookReviewDTO,
        UpdateBookReviewDTO
> {
        private final FileService fileService;

        public BookReviewService(
                @Qualifier("BookJpaRepository") BookReviewRepository repository,
                UserService userService,
                @Qualifier("TmdbGateway") FilmPlatformGateway gateway,
                FileService fileService
        ) {
                super(repository, userService, gateway);
                this.fileService = fileService;
        } 

        @Override
        protected void postProcessResume(BaseResume<BookItem> resume) {
                if (resume.getAuthorPhotoFileName() != null && !resume.getAuthorPhotoFileName().isBlank()) {
                        String presignedUrl = fileService.applyPresignedUrl(resume.getAuthorPhotoFileName());
                        resume.setAuthorPhotoUrl(presignedUrl);
                }
        }
        
        @Override
        protected BookReview createEntityInstance(User author, CreateBookReviewDTO dto, BookItem item) {
                return BookReview.builder()
                        .id(UUID.randomUUID().toString())
                        .author(author)
                        .textContent(dto.getTextContent())
                        .createdAt(LocalDateTime.now())

                        .bookId(item.getId())
                        .bookPlatform(item.getPlatformName())
                        .bookTitle(item.getTitle())
                        .bookAuthor(item.getAuthor())
                        .bookIsbn(item.getIsbn())
                        .bookPageCount(item.getPageCount())
                        .bookArtworkUrl(item.getArtworkUrl() != null ? item.getArtworkUrl().toString() : null)

                        .build();
        }        

        @Override
        protected void updateEntityInstance(BookReview entity, UpdateBookReviewDTO dto, BookItem newItem) {
                if (dto.getTextContent() != null && !dto.getTextContent().isBlank()) {
                        entity.setTextContent(dto.getTextContent());
                }

                if (newItem != null) {
                        entity.updateBookItem(newItem);
                }
        }        

        @Override
        protected void validateSpecificEntity(BookReview entity) {
                entity.validateBookReview();
        }        
}
