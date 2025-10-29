package com.imd.backend.infra.persistence.jpa.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @Column(nullable = false, name = "author_id")
  private String authorId;

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

  @CreationTimestamp
  @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;
}
