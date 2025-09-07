package com.imd.backend.core.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

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
