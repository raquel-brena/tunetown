package com.imd.backend.domain.valueobjects;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

import com.imd.backend.domain.valueobjects.tunableitem.TunableItemType;

public class TimeLineItem {
  private UUID tuneetId;
  private String textContent;
  private LocalDateTime createdAt;
  private long totalComments;
  private long totalLikes;

  // Dados do TunableItem
  private String tunableItemTitle;
  private String tunableItemArtist;
  private URI tunableItemArtworkUrl;
  private TunableItemType tunableItemType;

  // Dados do Autor
  private UUID authorId;
  private String authorUsername;
  private URI authorAvatarUrl;

  public TimeLineItem(UUID tuneetId, String textContent, LocalDateTime createdAt, long totalComments, long totalLikes,
      String tunableItemTitle, String tunableItemArtist, URI tunableItemArtworkUrl, TunableItemType tunableItemType,
      UUID authorId, String authorUsername, URI authorAvatarUrl) {
    this.tuneetId = tuneetId;
    this.textContent = textContent;
    this.createdAt = createdAt;
    this.totalComments = totalComments;
    this.totalLikes = totalLikes;
    this.tunableItemTitle = tunableItemTitle;
    this.tunableItemArtist = tunableItemArtist;
    this.tunableItemArtworkUrl = tunableItemArtworkUrl;
    this.tunableItemType = tunableItemType;
    this.authorId = authorId;
    this.authorUsername = authorUsername;
    this.authorAvatarUrl = authorAvatarUrl;
  }
  // Getters
  public UUID getTuneetId() {
    return tuneetId;
  }
  public String getTextContent() {
    return textContent;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public long getTotalComments() {
    return totalComments;
  }
  public long getTotalLikes() {
    return totalLikes;
  }
  public String getTunableItemTitle() {
    return tunableItemTitle;
  }
  public String getTunableItemArtist() {
    return tunableItemArtist;
  }
  public URI getTunableItemArtworkUrl() {
    return tunableItemArtworkUrl;
  }
  public TunableItemType getTunableItemType() {
    return tunableItemType;
  }
  public UUID getAuthorId() {
    return authorId;
  }
  public String getAuthorUsername() {
    return authorUsername;
  }
  public URI getAuthorAvatarUrl() {
    return authorAvatarUrl;
  }

  // Setters
  public void setAuthorAvatarUrl(URI url) {
    this.authorAvatarUrl = url;
  }
}
