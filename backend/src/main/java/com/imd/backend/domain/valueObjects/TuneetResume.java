package com.imd.backend.domain.valueObjects;

import java.time.LocalDateTime;
import java.util.UUID;

import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

  // Setters
  public void setUrlPhoto(String url) {
    this.urlPhoto = url;
  }
}
