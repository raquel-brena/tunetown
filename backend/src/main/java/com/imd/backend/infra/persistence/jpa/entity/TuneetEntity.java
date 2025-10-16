package com.imd.backend.infra.persistence.jpa.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tuneets")
@Data
@NoArgsConstructor
@AllArgsConstructor 
@EqualsAndHashCode(of = "id")
@Builder
public class TuneetEntity {
  @Id
  private UUID id;

  @Column(nullable = false)
  private UUID authorId;

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

}
