package com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistDTO(
  String id,
  String name
) {}
