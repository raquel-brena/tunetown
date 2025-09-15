package com.imd.backend.infra.persistence.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tuneets")
@Data
@NoArgsConstructor
@AllArgsConstructor 
@EqualsAndHashCode(of = "id")
public class TuneetEntity {
  @Id
  private String id;

  @Column(columnDefinition = "TEXT")
  private String contentText;

  private String tunableItemId;
  private String tunableItemPlataform;
  private String tunableItemTitle;
  private String tunableItemArtist;
  private String tunableItemType;
  private String tunableItemArtworkUrl;
}
