package com.imd.backend.domain.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.imd.backend.domain.exception.InvalidEntityAttributesException;

public class User {
  private UUID id;
  private String email;
  private String username;
  private String password;
  private String profileId;

  private User(
    UUID id,
    String email,
    String username,
    String password,
    String profileId
  ) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.profileId = profileId;

    this.validateAllAtributes();
  } 

  public static User createNew(
    String email,
    String username,
    String password,
    String profileId
  ) {
    final UUID id = UUID.randomUUID();

    return new User(id, email, username, password, profileId);
  }

  public static User rebuild(
    UUID id,
    String email,
    String username,
    String password,
    String profileId    
  ) {
    return new User(id, email, username, password, profileId);
  }


  private void validateAllAtributes() {
    final Map<String, String> errors = new HashMap<>();

    if(id == null)
      errors.put("id", "O ID do usuário não pode ser vazio");
    if(email == null || email.isBlank()) 
      errors.put("email", "O email do usuário não pode ser vazio");
    if(username == null || username.isBlank()) 
      errors.put("username", "O username não pode ser vazio");
    if(password == null || password.isBlank())
      errors.put("password", "O password deve possuir no mínimo 1 caractere");
    if(profileId == null || profileId.isBlank())
      errors.put("profileId", "O ID do profile não pode ser vazio");
    
    if(!errors.isEmpty())
      throw new InvalidEntityAttributesException("Atributos inválidos", errors);    
  }

  // Getters  
  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getProfileId() {
    return profileId;
  }  
}
