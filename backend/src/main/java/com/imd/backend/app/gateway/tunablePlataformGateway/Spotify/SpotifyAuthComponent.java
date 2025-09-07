package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.SpotifyTokenRequest;
import com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.SpotifyTokenResponse;

@Component
@FeignClient(
  name = "SpotifyAuthComponent",
  url = "https://accounts.spotify.com"
)
public class SpotifyAuthComponent {
  
  // @PostMapping(value = "/api/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  // SpotifyTokenResponse getToken(
  //   @RequestBody SpotifyTokenRequest request
  // );
}
