package com.imd.backend.infra.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private FileEntity photo;

    @Column(name = "favorite_song")
    private String favoriteSong;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL)
    private List<Follow> followers = new ArrayList<>();

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}