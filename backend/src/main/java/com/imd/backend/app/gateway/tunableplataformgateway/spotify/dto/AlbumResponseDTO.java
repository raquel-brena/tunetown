package com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlbumResponseDTO(
  String id,
  String name,
  List<ArtistDTO> artists,
  List<ImageDTO> images
){}
