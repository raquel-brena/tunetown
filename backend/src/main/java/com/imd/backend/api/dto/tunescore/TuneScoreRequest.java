package com.imd.backend.api.dto.tunescore;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

public record TuneScoreRequest(
        @NotBlank(message = "não deve estar em branco") @UUID(message = "deve ser um UUID válido") String originUserId,
        @NotBlank(message = "não deve estar em branco") @UUID(message = "deve ser um UUID válido") String destinationUserId
) { }
