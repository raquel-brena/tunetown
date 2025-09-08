package com.imd.backend.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.SpotifyAuthComponent;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SpotifyFeignConfig {
  private final SpotifyAuthComponent spotifyAuthComponent;

  @Bean
  RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      // Obtém um token válido
      final String token = spotifyAuthComponent.getValidAccessToken();

      // Adiciona o header authorization no formato "Bearer {token}"
      requestTemplate.header("Authorization", "Bearer " + token);
    };
  }
}
