package com.imd.backend.app.gateway.tunablePlataformGateway.Spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageDTO (
  String url
) {}
