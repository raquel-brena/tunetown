package com.imd.backend.app.service;

import com.imd.backend.api.dto.tunescore.TuneScoreResponse;
import com.imd.backend.app.gateway.llmGateway.OpenAiGateway;
import com.imd.backend.app.service.core.SimilarityService;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;
import com.imd.backend.infra.persistence.jpa.repository.TuneetRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TuneScoreService extends SimilarityService<Tuneet, TunableItem, TuneScoreResponse> {

    private static final String PROMPT = """
            # Tunetown - TuneScore

            Você é o analisador de compatibilidade musical do Tunetown, responsável por calcular o TuneScore entre usuários baseado em seus tuneets (posts sobre músicas, álbuns e podcasts do Spotify).

            ## Sua Missão

            Analise os tuneets de dois usuários e calcule uma porcentagem de similaridade que reflita o quanto seus gostos musicais e interesses se alinham. Seja criativo e otimista - sempre tente encontrar conexões, mesmo que sutis.

            ## Critérios de Análise

            Considere múltiplas dimensões para calcular a similaridade:

            1. **Artistas e músicas em comum** - Overlaps diretos nos tuneets
            2. **Gêneros musicais** - Preferências de gênero compartilhadas
            3. **Vibe e energia** - Músicas calmas vs agitadas, melancólicas vs animadas
            4. **Época/era** - Preferência por música atual, clássicos, retrô
            5. **Nicho vs mainstream** - Artistas populares vs independentes
            6. **Temas e contextos** - Músicas para treinar, relaxar, festejar
            7. **Subgêneros e micronichos** - Pop punk, lo-fi hip hop, indie folk, etc.
            8. **Idioma predominante** - Música em português, inglês, espanhol, etc.
            9. **Cultura e origem** - MPB, K-pop, música latina, indie brasileiro

            ## Diretrizes Importantes

            - **Seja generoso**: Evite ao máximo dar 0%% de similaridade. Sempre há algo em comum, mesmo que seja "ambos curtem música" ou "vibe eclética"
            - **Encontre conexões criativas**: Se não há overlap direto, busque similaridades de vibe, energia, contexto ou cultura
            - **Valorize a diversidade**: Se ambos têm gostos ecléticos, isso é uma conexão
            - **Pense em níveis**: Mesmo sem artistas em comum, pode haver gêneros, subgêneros ou vibes compartilhadas

            ## Tom e Estilo da Mensagem

            Sua mensagem deve ser:
            - **Cool e descontraída** - Use gírias moderadamente, seja natural
            - **Jovial e positiva** - Celebre as conexões encontradas
            - **Específica** - Mencione artistas, gêneros ou vibes concretas
            - **Concisa** - Máximo 2-3 frases, direto ao ponto
            - **Autêntica** - Evite ser forçadamente "jovem", seja genuíno

            ### Dados de Entrada
            Você receberá os tuneets de dois usuários. Cada item segue o formato:
            - Item: <Título> by <Artista> (<Tipo>) | Plataforma: <Plataforma> | Texto: <Conteúdo do post>

            Agora analise os tuneets e retorne o TuneScore:

            Usuário 1 (%s):
            %s

            Usuário 2 (%s):
            %s
            """;

    public TuneScoreService(
            @Qualifier("TuneetJpaRepository") TuneetRepository tuneetRepository,
            OpenAiGateway openAiGateway) {
        super(tuneetRepository, openAiGateway);
    }

    public TuneScoreResponse calculateTuneScore(String firstUserId, String secondUserId) {
        return calculateSimilarity(firstUserId, secondUserId);
    }

    @Override
    protected String buildPrompt(String firstUserId, String secondUserId, List<Tuneet> firstUserPosts,
            List<Tuneet> secondUserPosts) {
        String formattedFirstUserPosts = formatTuneets(firstUserPosts);
        String formattedSecondUserPosts = formatTuneets(secondUserPosts);

        return PROMPT.formatted(
                firstUserId,
                formattedFirstUserPosts,
                secondUserId,
                formattedSecondUserPosts);
    }

    @Override
    protected TuneScoreResponse buildResponse(String firstUserId, String secondUserId,
            SimilarityStructuredResponse response) {
        return new TuneScoreResponse(
                firstUserId,
                secondUserId,
                response.score(),
                response.message());
    }

    private String formatTuneets(List<Tuneet> tuneets) {
        if (tuneets == null || tuneets.isEmpty()) {
            return "- Nenhum tuneet encontrado.";
        }

        return tuneets.stream()
                .map(this::formatTuneet)
                .collect(Collectors.joining("\n"));
    }

    private String formatTuneet(Tuneet tuneet) {
        TunableItem item = tuneet.getTunableItem();

        return "- Item: %s by %s (%s) | Plataforma: %s | Texto: %s".formatted(
                item.getTitle(),
                item.getArtist(),
                item.getItemType(),
                item.getPlatformName(),
                tuneet.getTextContent());
    }
}
