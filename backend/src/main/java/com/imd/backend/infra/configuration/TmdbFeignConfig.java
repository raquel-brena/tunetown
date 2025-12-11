package com.imd.backend.infra.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class TmdbFeignConfig {

  @Value("${tmdb.api-key}")
  private String apiReadAccessToken;

  @Bean
  public RequestInterceptor tmdbRequestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Authorization", "Bearer " + apiReadAccessToken);
      requestTemplate.header("accept", "application/json");
    };
  }
}
