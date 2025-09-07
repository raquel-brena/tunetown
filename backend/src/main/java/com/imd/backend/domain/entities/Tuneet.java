package com.imd.backend.domain.entities;

import com.imd.backend.domain.entities.TunableItem.TunableItem;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Tuneet {

    private final UUID id;

    private String textContent;

    private TunableItem tunabbleItem;

    public Tuneet(String textContent, TunableItem tunabbleItem) {
        this.id = UUID.randomUUID();

        this.tunabbleItem = tunabbleItem;
    }
}
