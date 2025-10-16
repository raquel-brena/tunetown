package com.imd.backend.infra.persistence.jpa.mapper;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.imd.backend.domain.entities.TuneetResume;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;
import com.imd.backend.infra.persistence.jpa.entity.CommentEntity;
import com.imd.backend.infra.persistence.jpa.entity.LikeEntity;
import org.springframework.stereotype.Component;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.infra.persistence.jpa.entity.TuneetEntity;

@Component
public class TuneetJpaMapper {

    public static TuneetEntity fromTuneetDomain(Tuneet tuneet) {
        if (tuneet == null) return null;

        TuneetEntity tuneetEntity = TuneetEntity.builder()
                .id(tuneet.getId())
                .authorId(tuneet.getAuthorId())
                .contentText(tuneet.getTextContent())
                .tunableItemId(String.valueOf(tuneet.getTunableItem()))
                .tunableItemPlataform(tuneet.getTunableItem().getPlataformId())
                .tunableItemTitle(tuneet.getTunableItem().getTitle())
                .tunableItemArtist(tuneet.getTunableItem().getArtist())
                .tunableItemType(tuneet.getTunableItem().getItemType().toString())
                .tunableItemArtworkUrl(tuneet.getTunableItem().getArtworkUrl().toString())
                .build();

        // Comentários
        if (tuneet.getComments() != null) {
            List<CommentEntity> commentEntities = tuneet.getComments().stream()
                    .map(CommentMapper::toEntity)
                    .peek(c -> c.setTuneet(tuneetEntity))
                    .collect(Collectors.toList());
            tuneetEntity.setComments(commentEntities);
        }

        // Likes
        if (tuneet.getLikes() != null) {
            List<LikeEntity> likeEntities = tuneet.getLikes().stream()
                    .map(LikeMapper::toEntity)
                    .peek(l -> l.setTuneet(tuneetEntity))
                    .collect(Collectors.toList());
            tuneetEntity.setLikes(likeEntities);
        }

        return tuneetEntity;
    }



    public Tuneet tuneetFromJpaEntity(TuneetEntity entity) {
    return Tuneet.rebuild(
      entity.getId(),
      entity.getAuthorId(),
      entity.getContentText(),
      new TunableItem(
        entity.getTunableItemId(),
        entity.getTunableItemPlataform(),
        entity.getTunableItemTitle(),
        entity.getTunableItemArtist(),
        URI.create((entity.getTunableItemArtworkUrl())),
        TunableItemType.fromString(entity.getTunableItemType()))
    );
  }


    public static TuneetEntity fromTuneetResumeDomain(TuneetResume tuneet) {
        if (tuneet == null) return null;

        return TuneetEntity.builder()
                .id(tuneet.getId())
                .authorId(tuneet.getAuthorId())
                .contentText(tuneet.getTextContent())
                .tunableItemId(tuneet.getItemId())
                .tunableItemPlataform(tuneet.getItemPlataform())
                .tunableItemTitle(tuneet.getItemTitle())
                .tunableItemArtist(tuneet.getItemArtist())
                .tunableItemType(tuneet.getItemType() != null ? tuneet.getItemType().toString() : null)
                .tunableItemArtworkUrl(tuneet.getItemArtworkUrl() != null ? tuneet.getItemArtworkUrl().toString() : null)
                .build();
    }

    public static TuneetResume resumeFromTuneetJpaEntity(TuneetEntity entity) {
        if (entity == null) {
            return null;
        }

        return TuneetResume.builder()
                .id(entity.getId())
                .authorId(entity.getAuthorId())
                .textContent(entity.getContentText())
                .tunableItem(
                        TunableItem.builder()
                                .id(entity.getTunableItemId())
                                .plataformId(entity.getTunableItemPlataform())
                                .title(entity.getTunableItemTitle())
                                .artist(entity.getTunableItemArtist())
                                .artworkUrl(URI.create(entity.getTunableItemArtworkUrl()))
                                .itemType(TunableItemType.valueOf(entity.getTunableItemType()))
                                .build()
                )
                .build();
    }


}
