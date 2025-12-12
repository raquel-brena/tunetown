package com.imd.backend.app.dto.movie;

import com.imd.backend.app.dto.core.CreateBaseCommentDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMovieCommentDTO extends CreateBaseCommentDTO {

    private Integer minuteMark;
}
