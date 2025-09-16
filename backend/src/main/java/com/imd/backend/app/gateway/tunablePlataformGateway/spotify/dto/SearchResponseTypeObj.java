package com.imd.backend.app.gateway.tunablePlataformGateway.spotify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor 
@Data
public class SearchResponseTypeObj<T> {
  private Long total; // Número total de elementos desse tipo em específico
  private List<T> items; // Lista de items em si
}
