package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistDTO(
  String id,
  String name
) {}
