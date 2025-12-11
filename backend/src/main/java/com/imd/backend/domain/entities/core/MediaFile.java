package com.imd.backend.domain.entities.core;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private String url;

  private String contentType;

  private Long size;

  // --- FACTORY METHOD ---
  public static MediaFile create(String fileName, String url, String contentType, Long size) {
    MediaFile file = MediaFile.builder()
        .fileName(fileName)
        .url(url)
        .contentType(contentType)
        .size(size)
        .build();

    file.validateState();
    return file;
  }

  // --- VALIDAÇÕES ---
  private void validateState() {
    if (this.fileName == null || this.fileName.isBlank()) {
      throw new IllegalArgumentException("Nome do arquivo é obrigatório.");
    }
    if (this.url == null || this.url.isBlank()) {
      throw new IllegalArgumentException("URL do arquivo é obrigatória.");
    }
  }

  // --- EQUALS & HASHCODE ---
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof MediaFile))
      return false;
    MediaFile mediaFile = (MediaFile) o;
    if (id != null && mediaFile.id != null)
      return Objects.equals(id, mediaFile.id);
    return Objects.equals(url, mediaFile.url); // URL costuma ser única
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
