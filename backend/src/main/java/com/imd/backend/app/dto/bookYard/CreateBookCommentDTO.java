package com.imd.backend.app.dto.bookYard;

import com.imd.backend.app.dto.core.CreateBaseCommentDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateBookCommentDTO extends CreateBaseCommentDTO {

    private Integer pageNumber;
    private String chapterName;
}
