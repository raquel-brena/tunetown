package com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShowResponseDTO (
  String id,
  String name,
  String publisher,
  String description,
  List<ImageDTO> images
){}
