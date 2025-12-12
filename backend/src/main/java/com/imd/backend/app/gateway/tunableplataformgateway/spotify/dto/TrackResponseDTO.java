package com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrackResponseDTO (
  String id,
  String name,
  List<ArtistDTO> artists,
  AlbumResponseDTO album,
  @JsonProperty("duration_ms") int durationMs
) {}
