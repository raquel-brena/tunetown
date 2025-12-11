package com.imd.backend.app.service.bookyard;

import com.imd.backend.api.dto.similarity.SimilarityResponse;
import com.imd.backend.app.gateway.llmGateway.OpenAiGateway;
import com.imd.backend.app.service.core.SimilarityService;
import com.imd.backend.domain.entities.bookyard.BookReview;
import com.imd.backend.domain.valueobjects.bookitem.BookItem;
import com.imd.backend.infra.persistence.jpa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookSimilarityService extends SimilarityService<BookReview, BookItem, SimilarityResponse> {

    private static final String PROMPT = """
            Você é um assistente que compara perfis literários.
            Analise as resenhas abaixo de dois usuários e calcule uma similaridade (0-100%%) e uma mensagem curta.
            Considere autores, gêneros, temas, época (clássicos vs. contemporâneos), tamanho/preferência e tom.
            Seja positivo e específico; nunca devolva 0%%.

            Usuário 1 (%s):
            %s

            Usuário 2 (%s):
            %s
            """;

    public BookSimilarityService(
            @Qualifier("BookJpaRepository") BookRepository bookRepository,
            OpenAiGateway openAiGateway) {
        super(bookRepository, openAiGateway);
    }

    @Override
    protected String buildPrompt(String firstUserId, String secondUserId, List<BookReview> firstUserPosts,
            List<BookReview> secondUserPosts) {
        return PROMPT.formatted(
                firstUserId,
                formatReviews(firstUserPosts),
                secondUserId,
                formatReviews(secondUserPosts));
    }

    @Override
    protected SimilarityResponse buildResponse(String firstUserId, String secondUserId,
            SimilarityStructuredResponse response) {
        return new SimilarityResponse(firstUserId, secondUserId, response.score(), response.message());
    }

    private String formatReviews(List<BookReview> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return "- Nenhuma resenha encontrada.";
        }
        return reviews.stream()
                .map(this::formatReview)
                .collect(Collectors.joining("\n"));
    }

    private String formatReview(BookReview review) {
        BookItem item = review.getBookItem();
        return "- Livro: %s de %s (%s) | Plataforma: %s | Texto: %s".formatted(
                item.getTitle(),
                item.getAuthor(),
                item.getPageCount() != null ? item.getPageCount() + "p" : "-p",
                item.getPlatformName(),
                review.getTextContent());
    }
}
