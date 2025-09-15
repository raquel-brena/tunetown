package com.imd.backend.domain.entities;

import com.imd.backend.infra.persistence.jpa.entity.FileEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Profile {

    private String id;

    private String username;

    private String bio;

    private FileEntity photo;

    private String favoriteSong;

    private Date createdAt;

}