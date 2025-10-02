package com.imd.backend.infra.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    public UserEntity(String id, String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = null;
    }

    public UserEntity(String id, String email, String username, String password, ProfileEntity profile) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }    
}
