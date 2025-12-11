package com.imd.backend.app.gateway.filmplataformgateway.tmdb.dto;

import java.util.List;

public record TmdbCreditsDTO(
  List<TmdbCrewDTO> crew
) {}
