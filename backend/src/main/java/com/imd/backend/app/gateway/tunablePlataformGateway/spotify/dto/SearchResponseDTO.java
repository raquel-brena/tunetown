package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponseDTO(
  SearchResponseTypeObj<AlbumResponseDTO> albums,
  SearchResponseTypeObj<TrackResponseDTO> tracks,
  SearchResponseTypeObj<ShowResponseDTO> shows
) {}
