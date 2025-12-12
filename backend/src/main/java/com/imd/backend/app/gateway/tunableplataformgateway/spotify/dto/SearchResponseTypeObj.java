package com.imd.backend.app.gateway.tunableplataformgateway.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor 
@Data
public class SearchResponseTypeObj<T> {
  private Long total; // Número total de elementos desse tipo em específico
  private List<T> items; // Lista de items em si
}
