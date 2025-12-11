package com.imd.backend.domain.valueObjects.core;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseTimelineItem<I extends PostItem> {
  // Dados do Post
  private UUID postId;
  private String textContent;
  private LocalDateTime createdAt;

  private Integer totalComments;
  private Integer totalLikes;

  // Dados do Autor
  private UUID authorId;
  private String authorUsername;
  private String authorAvatarUrl;

  // O Item Rico
  private I item;

  // Construtor "JPA Compliant" (A ordem importa!)
  public BaseTimelineItem(
      String postId,
      String textContent,
      LocalDateTime createdAt,
      Integer totalComments,
      Integer totalLikes,
      String authorId,
      String authorUsername,
      String authorAvatarUrl,
      I item) {
    this.postId = UUID.fromString(postId);
    this.textContent = textContent;
    this.createdAt = createdAt;
    this.totalComments = totalComments;
    this.totalLikes = totalLikes;
    this.authorId = UUID.fromString(authorId);
    this.authorUsername = authorUsername;
    this.authorAvatarUrl = authorAvatarUrl;
    this.item = item;
  }
}
