package com.imd.backend.domain.valueObjects;

import java.util.Date;
import java.util.UUID;

public record UserWithProfile (
  UUID userId,
  String userEmail,
  String username,
  String profileId,
  String bio,
  String favoriteSong,
  Date createdAt
) {}
