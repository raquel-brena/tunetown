package com.imd.backend.domain.valueObjects;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserWithProfile {
  private UUID userId;
  private String userEmail;
  private String username;
  private String profileId;
  private String bio;
  private String favoriteSong;
  private LocalDateTime createdAt;
  private String photoUrl;
  private String photoFileName;

  public UserWithProfile(UUID userId, String userEmail, String username, String profileId, String bio,
      String favoriteSong, LocalDateTime createdAt, String photoFileName) {
    this.userId = userId;
    this.userEmail = userEmail;
    this.username = username;
    this.profileId = profileId;
    this.bio = bio;
    this.favoriteSong = favoriteSong;
    this.createdAt = createdAt;
    this.photoFileName = photoFileName;
  }

  // Getters
  public UUID getUserId() {
    return userId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public String getUsername() {
    return username;
  }

  public String getProfileId() {
    return profileId;
  }

  public String getBio() {
    return bio;
  }

  public String getFavoriteSong() {
    return favoriteSong;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public String getPhotoFileName() {
    return photoFileName;
  }  
  
  // Setters
  public void setPhotoUrl(String url) {
    if(url != null && !url.isBlank())
      this.photoUrl = url;
  }
}
