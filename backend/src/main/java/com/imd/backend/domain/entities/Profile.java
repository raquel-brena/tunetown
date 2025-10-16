package com.imd.backend.domain.entities;

import com.imd.backend.infra.persistence.jpa.entity.FileEntity;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Profile {
    private UUID id;
    private String bio;
    private Long userId;
    private FileEntity photo;
    private String favoriteSong;
    private Date createdAt;
}