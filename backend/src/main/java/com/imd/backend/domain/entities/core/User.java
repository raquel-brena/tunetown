package com.imd.backend.domain.entities.core;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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

  // O relacionamento inverso (OneToMany) geralmente fica na classe concreta
  // ou pode ser omitido se não for estritamente necessário para o framework core.
  
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
