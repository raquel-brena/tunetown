package com.imd.backend.infra.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String bio;

    private String avatarUrl;

    private String favoriteSong;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}