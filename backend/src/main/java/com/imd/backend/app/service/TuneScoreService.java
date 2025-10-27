package com.imd.backend.app.service;

import com.imd.backend.api.dto.tunescore.TuneScoreResponse;
import com.imd.backend.api.dto.tunescore.TuneScoreStructuredResponse;
import com.imd.backend.app.gateway.llmGateway.OpenAiGateway;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.valueObjects.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TuneScoreService {
    private final TuneetService tuneetService;
    private final OpenAiGateway openAiGateway;

    @Autowired
    public TuneScoreService(TuneetService tuneetService, OpenAiGateway openAiGateway) {
        this.tuneetService = tuneetService;
        this.openAiGateway = openAiGateway;
    }

    public TuneScoreResponse calculateTuneScore(String firstUserId, String secondUserId) {
        var pagination = new Pagination(0, 50, "id", "DESC");

        var firstUserTuneets = tuneetService.findTuneetsByAuthorId(firstUserId, pagination);
        var secondUserTuneets = tuneetService.findTuneetsByAuthorId(secondUserId, pagination);

        var firstUserTunableItems = firstUserTuneets.itens().stream()
                .map(Tuneet::getTunableContent).toList();

        var secondUserTunableItems = secondUserTuneets.itens().stream()
               .map(Tuneet::getTunableContent).toList();

        var response = openAiGateway.structuredCall("""
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
            
            ### Exemplos de tom adequado:
            - "Vocês dois manjam de indie alternativo! Pegada meio melancólica mas com energia boa"
            - "Vibe eclética detectada! Um curte trap, outro indie rock, mas ambos tão ligados em sons autorais"
            - "Rolê sonoro parecido: MPB raiz, mas sempre abertos pra um pop chiclete no meio"
            
            ### Dados de Entrada
            Você receberá os tuneets de dois usuários no seguinte formato:
            
            Usuário 1:
            [Lista de tuneets com informações sobre músicas, álbuns, artistas, etc]
            Usuário 2:
            [Lista de tuneets com informações sobre músicas, álbuns, artistas, etc]
            
            ## Exemplos de mensagens
            1 - Alta similaridade (78%%): "Match perfeito no indie brasileiro! Vocês manjam de Terno Rei, Tim Bernardes e toda essa vibe contemplativa"
            2 - Média similaridade (45%%): "Gostos diferentes mas energy parecida: um no trap BR, outro no pop punk, mas ambos curtem som com atitude"
            3 - Baixa similaridade mas positiva (22%%): "Vocês são opostos que se atraem! Um é team sertanejo raiz, outro é indie gringo, mas os dois amam umas lyrics bem escritas"
            4 - Mínima mas nunca zero (8%%): "Universos totalmente diferentes, mas hey, vocês dois têm rolê sonoro autêntico e isso é raro! Troca de playlist vai ser interessante"
            
            ## Lembre-se
            
            - Nunca retorne 0%% - sempre há algo em comum, mesmo que seja a atitude em relação à música
            - Seja honesto mas positivo - se a similaridade é baixa, celebre a diferença
            - Mencione elementos concretos que conectam os usuários
            - Mantenha o tom leve e divertido - afinal, música é sobre conexão e descoberta
            
            Agora analise os tuneets fornecidos e retorne o TuneScore:
            
            Usuário 1: %s
            Usuário 2: %s
            """.formatted(firstUserTunableItems, secondUserTunableItems), TuneScoreStructuredResponse.class);

        return new TuneScoreResponse(
                firstUserId.toString(),
                secondUserId.toString(),
                response.score(),
                response.message()
        );
    }
}
