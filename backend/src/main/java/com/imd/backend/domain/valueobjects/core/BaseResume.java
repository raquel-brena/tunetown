package com.imd.backend.domain.valueobjects.core;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class BaseResume<I extends PostItem> {

  // Dados do Post
  private UUID id;
  private String textContent;
  private LocalDateTime createdAt;

  // Dados do Autor
  private UUID authorId;
  private String authorUsername;
  private String authorEmail;
  private String authorBio;
  private String authorPhotoFileName; // Nome do arquivo (para ser assinado depois)
  private String authorPhotoUrl; // URL final

  // Métricas
  private Integer totalComments;
  private Integer totalLikes;
  private Integer totalFollowers;
  private Integer totalFollowing;

  // O Item Rico
  private I item;

  // Construtor compatível com JPA (SELECT NEW)
  public BaseResume(
      String id, String textContent, LocalDateTime createdAt,
      String authorId, String authorUsername, String authorEmail, String authorBio, String authorPhotoFileName,
      Integer totalComments, Integer totalLikes, Integer totalFollowers, Integer totalFollowing,
      I item) {
    this.id = UUID.fromString(id);
    this.textContent = textContent;
    this.createdAt = createdAt;
    this.authorId = UUID.fromString(authorId);
    this.authorUsername = authorUsername;
    this.authorEmail = authorEmail;
    this.authorBio = authorBio;
    this.authorPhotoFileName = authorPhotoFileName;
    this.totalComments = totalComments;
    this.totalLikes = totalLikes;
    this.totalFollowers = totalFollowers;
    this.totalFollowing = totalFollowing;
    this.item = item;
  }
}
