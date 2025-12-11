package com.imd.backend.app.gateway.filmPlataformGateway.tmdb;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbMovieDetailDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbSearchResponseDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbSearchSeriesResponseDTO;
import com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto.TmdbSeriesDetailDTO;
import com.imd.backend.infra.configuration.TmdbFeignConfig;

@FeignClient(name = "TmdbApiClient", url = "https://api.themoviedb.org/3", configuration = TmdbFeignConfig.class)
public interface TmdbApiClient {

  /**
   * Busca filmes por texto.
   */
  @GetMapping("/search/movie")
  TmdbSearchResponseDTO searchMovies(
      @RequestParam("query") String query,
      @RequestParam(value = "language", defaultValue = "pt-BR") String language);

  /**
   * Busca detalhes do filme.
   * 'append_to_response=credits' é necessário para pegar o Diretor.
   */
  @GetMapping("/movie/{id}")
  TmdbMovieDetailDTO getMovieById(
      @PathVariable("id") String id,
      @RequestParam(value = "language", defaultValue = "pt-BR") String language,
      @RequestParam(value = "append_to_response", defaultValue = "credits") String append);

  @GetMapping("/search/tv")
  TmdbSearchSeriesResponseDTO searchSeries(
      @RequestParam("query") String query,
      @RequestParam(value = "language", defaultValue = "pt-BR") String language);

  @GetMapping("/tv/{id}")
  TmdbSeriesDetailDTO getSeriesById(
      @PathVariable("id") String id,
      @RequestParam(value = "language", defaultValue = "pt-BR") String language
  // Para séries, muitas vezes o "criador" já vem no detalhe, sem precisar de
  // append
  );        
}
