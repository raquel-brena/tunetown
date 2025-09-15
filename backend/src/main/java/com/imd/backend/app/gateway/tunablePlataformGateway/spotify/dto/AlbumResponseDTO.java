package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlbumResponseDTO(
  String id,
  String name,
  List<ArtistDTO> artists,
  List<ImageDTO> images
){}
