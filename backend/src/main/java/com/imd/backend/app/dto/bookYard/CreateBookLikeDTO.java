package com.imd.backend.app.dto.bookYard;

import com.imd.backend.app.dto.core.CreateBaseLikeDTO;
import com.imd.backend.domain.valueobjects.bookitem.ImpactLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookLikeDTO extends CreateBaseLikeDTO {
    @NotNull
    private ImpactLevel impactLevel;

}
