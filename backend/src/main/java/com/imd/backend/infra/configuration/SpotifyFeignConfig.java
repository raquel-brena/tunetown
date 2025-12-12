package com.imd.backend.infra.configuration;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.SpotifyAuthComponent;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class SpotifyFeignConfig {
  @Bean
  public RequestInterceptor spotifyRequestInterceptor(SpotifyAuthComponent spotifyAuthComponent) {
    return requestTemplate -> {
      final String token = spotifyAuthComponent.getValidAccessToken();

      requestTemplate.header("Authorization", "Bearer " + token);
    };
  }
}
