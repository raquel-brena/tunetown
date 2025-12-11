package com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShowResponseDTO (
  String id,
  String name,
  String publisher,
  String description,
  List<ImageDTO> images
){}
