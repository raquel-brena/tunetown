package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrackResponseDTO (
  String id,
  String name,
  List<ArtistDTO> artists,
  AlbumResponseDTO album,
  @JsonProperty("duration_ms") int durationMs
) {}
