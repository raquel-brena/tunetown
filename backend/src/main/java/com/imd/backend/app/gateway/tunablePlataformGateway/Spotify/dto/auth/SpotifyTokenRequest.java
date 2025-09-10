package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto.auth;

import feign.form.FormProperty;

public record SpotifyTokenRequest(
        @FormProperty("grant_type") String grantType,
        @FormProperty("client_id") String clientId,
        @FormProperty("client_secret") String clientSecret
) {
}
