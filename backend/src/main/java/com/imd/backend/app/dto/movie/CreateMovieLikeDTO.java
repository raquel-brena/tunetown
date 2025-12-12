package com.imd.backend.app.dto.movie;

import com.imd.backend.app.dto.core.CreateBaseLikeDTO;
import com.imd.backend.domain.valueobjects.movieitem.MovieReaction;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieLikeDTO extends CreateBaseLikeDTO {
    @NotNull
    private MovieReaction reaction;
}
