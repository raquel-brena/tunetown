package com.imd.backend.domain.entities.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter // Setters podem ser úteis para User, mas cuidado
@NoArgsConstructor
@AllArgsConstructor 
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
  @Id
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private String username;

  private String password;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore // evita recursão infinita ao serializar user -> profile -> user
  private Profile profile;

    public User(String id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = null;
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
