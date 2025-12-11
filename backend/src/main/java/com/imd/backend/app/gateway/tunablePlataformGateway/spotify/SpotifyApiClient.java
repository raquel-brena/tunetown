package com.imd.backend.app.gateway.tunablePlataformGateway.spotify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.AlbumResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.SearchResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.ShowResponseDTO;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.TrackResponseDTO;
import com.imd.backend.infra.configuration.SpotifyFeignConfig;

@FeignClient(
  name = "SpotifyApiClient",
  url = "https://api.spotify.com/v1",
  configuration = SpotifyFeignConfig.class
)
public interface SpotifyApiClient {
  /**
   * Busca um álbum específico pelo seu ID.
   * Documentação: https://developer.spotify.com/documentation/web-api/reference/get-an-album
   * @param id O ID do álbum no Spotify.
   * @return Os detalhes do álbum.
   */
  @GetMapping("/albums/{id}")
  public AlbumResponseDTO getAlbumById(
    @PathVariable(required = true) String id
  );  

  /**
   * Busca uma música específica pelo seu ID.
   * Documentação:
   * https://developer.spotify.com/documentation/web-api/reference/get-track
   * 
   * @param id O ID da música no Spotify.
   * @return Os detalhes da música.
   */
  @GetMapping("/tracks/{id}")
  public TrackResponseDTO getTrackById(
    @PathVariable(required = true) String id
  );  

  /**
   * Busca um podcast (Show) específico pelo seu ID.
   * Documentação:
   * https://developer.spotify.com/documentation/web-api/reference/get-a-show
   * 
   * @param id O ID do show no Spotify.
   * @return Os detalhes do podcast.
   */
  @GetMapping("/shows/{id}")
  public ShowResponseDTO getShowById(
    @PathVariable(required = true) String id
  );  

  /**
   * Um exemplo mais avançado: Busca genérica por itens.
   * Documentação: https://developer.spotify.com/documentation/web-api/reference/search
   * @param query Termo de busca (e.g., "Guns N' Roses").
   * @param type Tipo de item a ser buscado (e.g., "album", "track", "show"). Pode ser uma lista separada por vírgula.
   * @return Um objeto complexo com os resultados da busca. Você precisará criar DTOs para mapeá-lo.
   */
  @GetMapping("/search")
  SearchResponseDTO search(@RequestParam("q") String query, @RequestParam(required = true) String type);  
}
