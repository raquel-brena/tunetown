package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto;

public record SpotifyTokenRequest(
        String grantType,
        String clientId,
        String clientSecret
) {
}
