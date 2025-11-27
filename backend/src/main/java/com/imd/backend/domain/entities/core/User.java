package com.imd.backend.domain.entities.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.imd.backend.domain.entities.tunetown.Tuneet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter // Setters podem ser úteis para User, mas cuidado
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class User {
  @Id
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private String username;

  private String password;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Profile profile;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Tuneet> tuneets = new ArrayList<>();

  // O relacionamento inverso (OneToMany) geralmente fica na classe concreta
  // ou pode ser omitido se não for estritamente necessário para o framework core.

    public User(String id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = null;
    }

    public User(String id, String email, String username, String password, Profile profile) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof User user)) return false;
      return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
      return Objects.hash(id);
  }  

  @PrePersist
  private void prePersist() {
      if (this.id == null) {
          this.id = UUID.randomUUID().toString();
      }
  }  
}
