package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

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
