package com.imd.backend.app.gateway.filmPlataformGateway.tmdb.dto;

import java.util.List;

public record TmdbCreditsDTO(
  List<TmdbCrewDTO> crew
) {}
