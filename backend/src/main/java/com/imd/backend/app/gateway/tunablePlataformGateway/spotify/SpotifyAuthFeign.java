package com.imd.backend.app.gateway.tunablePlataformGateway.spotify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto.auth.SpotifyTokenResponse;

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
