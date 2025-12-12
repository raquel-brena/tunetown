package com.imd.backend.app.gateway.tunableplataformgateway.spotify;

import com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto.auth.SpotifyTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
  name = "SpotifyAuthComponent",
  url = "https://accounts.spotify.com"
)
public interface SpotifyAuthFeign {

    @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public SpotifyTokenResponse getToken(
      @RequestParam("grant_type") String grantType,
      @RequestParam("client_id") String clientId,
      @RequestParam("client_secret") String clientSecret
    );
}
