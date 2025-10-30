package com.imd.backend.domain.valueObjects;

import java.time.LocalDateTime;
import java.util.UUID;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

public class TuneetResume {
  private UUID id;
  private String contentText;
  private String tunableItemArtist;
  private String tunableItemTitle;
  private String tunableItemArtworkUrl;
  private String tunableItemId;
  private String tunableItemPlataform;
  private TunableItemType tunableItemType;
  private LocalDateTime createdAt;
  private String username;
  private String profileId;
  private String email;
  private UUID authorId;
  private long totalComments;
  private long totalLikes;
  private String bio;
  private long totalFollowers;
  private long totalFollowing;
  private String urlPhoto;
  private String fileNamePhoto;

  public TuneetResume(UUID id, String contentText, String tunableItemArtist, String tunableItemTitle,
      String tunableItemArtworkUrl, String tunableItemId, String tunableItemPlataform, TunableItemType tunableItemType,
      LocalDateTime createdAt, String username, String profileId, String email, UUID authorId, long totalComments,
      long totalLikes, String bio, long totalFollowers, long totalFollowing, String urlPhoto, String fileNamePhoto) {
    this.id = id;
    this.contentText = contentText;
    this.tunableItemArtist = tunableItemArtist;
    this.tunableItemTitle = tunableItemTitle;
    this.tunableItemArtworkUrl = tunableItemArtworkUrl;
    this.tunableItemId = tunableItemId;
    this.tunableItemPlataform = tunableItemPlataform;
    this.tunableItemType = tunableItemType;
    this.createdAt = createdAt;
    this.username = username;
    this.profileId = profileId;
    this.email = email;
    this.authorId = authorId;
    this.totalComments = totalComments;
    this.totalLikes = totalLikes;
    this.bio = bio;
    this.totalFollowers = totalFollowers;
    this.totalFollowing = totalFollowing;
    this.fileNamePhoto = fileNamePhoto;
    this.urlPhoto = urlPhoto;
  }

  public UUID getId() {
    return id;
  }

  public String getContentText() {
    return contentText;
  }

  public String getTunableItemArtist() {
    return tunableItemArtist;
  }

  public String getTunableItemTitle() {
    return tunableItemTitle;
  }

  public String getTunableItemArtworkUrl() {
    return tunableItemArtworkUrl;
  }

  public String getTunableItemId() {
    return tunableItemId;
  }

  public String getTunableItemPlataform() {
    return tunableItemPlataform;
  }

  public TunableItemType getTunableItemType() {
    return tunableItemType;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getUsername() {
    return username;
  }

  public String getProfileId() {
    return profileId;
  }

  public String getEmail() {
    return email;
  }

  public UUID getAuthorId() {
    return authorId;
  }

  public long getTotalComments() {
    return totalComments;
  }

  public long getTotalLikes() {
    return totalLikes;
  }

  public String getBio() {
    return bio;
  }

  public long getTotalFollowers() {
    return totalFollowers;
  }

  public long getTotalFollowing() {
    return totalFollowing;
  }

  public String getUrlPhoto() {
    return urlPhoto;
  }

  public String getFileNamePhoto() {
    return fileNamePhoto;
  }  

  // Setters
  public void setUrlPhoto(String url) {
    this.urlPhoto = url;
  }
}
