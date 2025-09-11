package com.imd.backend.domain.entities;

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

    private String bio;

    private String avatarUrl;

    private String favoriteSong;

    private Date createdAt;

}