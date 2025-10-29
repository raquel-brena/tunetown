package com.imd.backend.domain.entities;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.imd.backend.domain.exception.InvalidEntityAttributesException;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.entity.FileEntity;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa um "post" (tuneet) de um usuário
 */
@Getter
@Setter
public class Tuneet {
  private final UUID id;
  private String textContent;
  private String authorId;
  private String authorName;
  private UserEntity author;
  private TunableItem tunableItem;
  private long totalComments;
  private long totalLikes;
  LocalDateTime createdAt;

  private Tuneet(UUID id, String textContent, TunableItem tunableItem) {
    this.id = id;
    this.textContent = textContent;
    this.tunableItem = tunableItem;
    this.validateAttributes();
  }

    public Tuneet(
            String id,
            String contentText,
            LocalDateTime createdAt,
            String authorName,
            long totalComments,
            long totalLikes
    ) {
        this.id =  UUID.fromString(id);
        this.textContent = contentText;
        this.createdAt = createdAt;
        this.authorName = authorName;
        this.totalComments = totalComments;
        this.totalLikes = totalLikes;
    }


    public static Tuneet createNew(UserEntity user, String textContent, TunableItem item) {
    final UUID id = UUID.randomUUID();
       Tuneet tuneet =  new Tuneet(id, textContent, item);
       tuneet.setAuthor(user);
        return tuneet;
  }

    public static Tuneet rebuild(
            UUID id,
            String authorName,
            String profileId,
            String email,
            String authorId,
            String textContent,
            TunableItem item,
            LocalDateTime createdAt,
            long totalComments,
            long totalLikes,
            String bio,
            long totalFollowers,
            long totalFollowing,
            String urlPhoto
    ) {
        Tuneet tuneet = new Tuneet(id, textContent, item);
        UserEntity user = new UserEntity();
        user.setUsername(authorName);
        user.setEmail(email);
        user.setId(authorId);

        ProfileEntity profile = new ProfileEntity();
        profile.setId(profileId);
        profile.setBio(bio);
        profile.setTotalFollowers(totalFollowers);
        profile.setTotalFollowing(totalFollowing);

        FileEntity  file = new FileEntity();
        file.setUrl(urlPhoto);

        profile.setPhoto(file);

        user.setProfile(profile);

        tuneet.setAuthor(user);
        tuneet.setCreatedAt(createdAt);
        tuneet.setTotalComments(totalComments);
        tuneet.setTotalLikes(totalLikes);
        return tuneet;
    }

    // Getters
  public String getItemId() {
    return this.tunableItem.getItemId();
  }

  public String getItemPlataform() {
    return this.tunableItem.getPlataformId();
  }

  public String getItemTitle() {
    return this.tunableItem.getTitle();
  }

  public String getItemArtist() {
    return this.tunableItem.getArtist();
  }

  public URI getItemArtworkUrl() {
    return this.tunableItem.getArtworkUrl();
  }

  public TunableItemType getItemType() {
    return this.tunableItem.getItemType();
  }

  // Setters
  public void setTextContent(String textContent) {
    this.textContent = textContent;
    this.validateAttributes();
  }
  
  public void setTunableItem(TunableItem tunableItem) {
    this.tunableItem = tunableItem;
    this.validateAttributes();
  }

  // Private methods
  private void validateAttributes() {
    final Map<String, String> errors = new HashMap<>();
    
    if (id == null)
      errors.put("id", "O ID não pode ser nulo");

    if (textContent == null || textContent.isBlank())
      errors.put("textContent", "O conteúdo de texto do tuneet não pode estar vazio");

    if (tunableItem == null)
      errors.put("tunableItem", "O item tunetável não pode ser nulo");

    if (!errors.isEmpty())
      throw new InvalidEntityAttributesException("Atributos inválidos", errors);    
  }

    public String getTunableContent() {
      return String.format("%s by %s (%s)",
        tunableItem.getTitle(),
        tunableItem.getArtist(),
        tunableItem.getItemType()
      );
    }
}
