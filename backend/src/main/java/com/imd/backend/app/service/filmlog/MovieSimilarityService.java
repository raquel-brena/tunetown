package com.imd.backend.app.service.filmlog;

import com.imd.backend.api.dto.similarity.SimilarityResponse;
import com.imd.backend.app.gateway.llmGateway.OpenAiGateway;
import com.imd.backend.app.service.core.SimilarityService;
import com.imd.backend.domain.entities.filmlog.MovieReview;
import com.imd.backend.domain.valueobjects.movieitem.MovieItem;
import com.imd.backend.infra.persistence.jpa.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieSimilarityService extends SimilarityService<MovieReview, MovieItem, SimilarityResponse> {

    private static final String PROMPT = """
        Você é um assistente que compara gostos de filmes/séries.
        Analise as resenhas abaixo e calcule uma similaridade (0-100%%) com uma mensagem curta.
        Considere: gêneros, diretores, ano/era, tipo (filme/série), tom, notas e plataformas.
        Seja positivo e específico; nunca devolva 0%%.

        Usuário 1 (%s):
        %s

        Usuário 2 (%s):
        %s
        """;

    public MovieSimilarityService(
            @Qualifier("MovieJpaRepository") MovieRepository movieRepository,
            OpenAiGateway openAiGateway
    ) {
        super(movieRepository, openAiGateway);
    }

    @Override
    protected String buildPrompt(String firstUserId, String secondUserId, List<MovieReview> firstUserPosts, List<MovieReview> secondUserPosts) {
        return PROMPT.formatted(
                firstUserId,
                formatReviews(firstUserPosts),
                secondUserId,
                formatReviews(secondUserPosts)
        );
    }

    @Override
    protected SimilarityResponse buildResponse(String firstUserId, String secondUserId, SimilarityStructuredResponse response) {
        return new SimilarityResponse(firstUserId, secondUserId, response.score(), response.message());
    }

    private String formatReviews(List<MovieReview> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return "- Nenhuma resenha encontrada.";
        }
        return reviews.stream()
                .map(this::formatReview)
                .collect(Collectors.joining("\n"));
    }

    private String formatReview(MovieReview review) {
        MovieItem item = review.getMovieItem();
        return "- Título: %s (%s) dir. %s [%s] | Plataforma: %s | Nota: %s | Texto: %s".formatted(
                item.getTitle(),
                item.getReleaseYear() != null ? item.getReleaseYear() : "-",
                item.getDirector() != null ? item.getDirector() : "-",
                item.getItemType(),
                item.getPlatformName(),
                review.getRating() != null ? review.getRating() : "-",
                review.getTextContent()
        );
    }
}
