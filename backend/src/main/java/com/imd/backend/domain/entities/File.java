package com.imd.backend.domain.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class File {

    private Long id;

    private String fileName;

    private String url;

    private String contentType;

    private Long size;
}
