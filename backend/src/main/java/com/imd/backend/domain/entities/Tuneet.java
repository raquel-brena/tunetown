package com.imd.backend.domain.entities;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.imd.backend.domain.exception.InvalidEntityAttributesException;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

/**
 * Representa um "post" (tuneet) de um usuário
 */
public class Tuneet {
    private String id;
    private String textContent;
    private String authorId;
    private TunableItem tunableItem;

    public Tuneet(String id, String authorId, String textContent, TunableItem tunableItem) {

        this.id = id;
        this.authorId = authorId;
        this.textContent = textContent;
        this.tunableItem = tunableItem;
        this.validateAttributes();
    }

    public Tuneet(String textContent, TunableItem tunableItem) {
        this.tunableItem = tunableItem;
        this.textContent = textContent;
    }

    public Tuneet (String authorId, String textContent, TunableItem item) {
        this.authorId = authorId;
        this.textContent = textContent;
        this.tunableItem = item;
    }

    public static Tuneet rebuild(
            String id,
            String authorId,
            String textContent,
            TunableItem item
    ) {
        return new Tuneet(id, authorId, textContent, item);
    }

    // Getters
    public String getId() {
        return this.id;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public String getTextContent() {
        return this.textContent;
    }

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