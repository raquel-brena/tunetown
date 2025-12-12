package com.imd.backend.app.gateway.tunableplataformgateway.spotify;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto.auth.SpotifyTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SpotifyAuthComponent {
  private final SpotifyAuthFeign webClient;
  private final String clientId;
  private final String clientSecret;

  // Variáveis para guardar o token em cache
  private String cachedToken;
  private Instant tokenExpirationTime;

  private static final String AUTH_GRANT_TYPE = "client_credentials";

  public SpotifyAuthComponent(
    @Value("${spotify.client-id}") String clientId,
    @Value("${spotify.client-secret}") String clientSecret,
    SpotifyAuthFeign webAuthFeign
  ) {
    this.webClient = webAuthFeign;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  /**
   * Método principal. Retorna um token válido, buscando um novo se necessário.
   * É sincronizado para evitar que múltiplas threads busquem um token ao mesmo
   * tempo.
   */
  public synchronized String getValidAccessToken() {
    if (cachedToken == null || Instant.now().isAfter(tokenExpirationTime)) {
      this.fetchNewAccessToken();
    }

    return cachedToken;
  }  

  private void fetchNewAccessToken() {    
    final SpotifyTokenResponse tokenResponse = this.webClient.getToken(
      AUTH_GRANT_TYPE,
      clientId,
      clientSecret
    );

    if(tokenResponse == null)
      throw new RuntimeException("Não foi possível obter o token de acesso do spotify");
    
    this.cachedToken = tokenResponse.accessToken();
    // Define a expiração com 60s de margem de segurança para evitar problemas
    this.tokenExpirationTime = Instant.now().plusSeconds(tokenResponse.expiresIn() - 60);    
  }
}
