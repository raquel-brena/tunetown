package com.imd.backend.app.gateway.tunablePlataformGateway.spotify;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.imd.backend.app.gateway.tunablePlataformGateway.TunablePlataformGateway;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.factory.SpotifyItemByIdStrategyFactory;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.factory.SpotifySearchItemStrategyFactory;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifyItemById.SpotifyItemByIdStrategy;
import com.imd.backend.app.gateway.tunablePlataformGateway.spotify.strategy.spotifySearchItem.SpotifySearchItemStrategy;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItem;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

import lombok.RequiredArgsConstructor;

@Component
@Qualifier("SpotifyGateway")
@RequiredArgsConstructor
public class SpotifyGateway implements TunablePlataformGateway {
    private final SpotifyItemByIdStrategyFactory itemByIdStrategyFactory;
    private final SpotifySearchItemStrategyFactory searchItemStrategyFactory;

    @Override
    public List<TunableItem> searchItem(String query, TunableItemType itemType) {
        final SpotifySearchItemStrategy searchItemStrategy = this.searchItemStrategyFactory.create(itemType);

        return searchItemStrategy.execute(query);
    }

    @Override
    public TunableItem getItemById(String id, TunableItemType itemType){
        final SpotifyItemByIdStrategy findByIdStrategy = this.itemByIdStrategyFactory.create(itemType);

        return findByIdStrategy.execute(id);
    }
}