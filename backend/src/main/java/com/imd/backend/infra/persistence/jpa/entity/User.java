package com.imd.backend.infra.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name="users")
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String email;
    private String username;
    private String password;
}
