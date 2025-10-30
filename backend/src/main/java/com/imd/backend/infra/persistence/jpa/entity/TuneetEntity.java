package com.imd.backend.infra.persistence.jpa.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tuneets")
@Data
@NoArgsConstructor
@AllArgsConstructor 
@EqualsAndHashCode(of = "id")
@Builder
public class TuneetEntity {
  @Id
  private String id;

  @ManyToOne(fetch = FetchType.LAZY) 
  @JoinColumn(name = "author_id", nullable = false) // Mapeia para a coluna FK
  private UserEntity author;

  @Column(columnDefinition = "TEXT")
  private String contentText;

  @OneToMany(mappedBy = "tuneet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CommentEntity> comments;

  @OneToMany(mappedBy = "tuneet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<LikeEntity> likes;

  private String tunableItemId;
  private String tunableItemPlataform;
  private String tunableItemTitle;
  private String tunableItemArtist;
  private String tunableItemType;
  private String tunableItemArtworkUrl;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
}
