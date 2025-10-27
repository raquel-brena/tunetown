package com.imd.backend.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.valueObjects.PageResult;
import com.imd.backend.domain.valueObjects.Pagination;
import com.imd.backend.domain.valueObjects.TrendingTuneResult;
import com.imd.backend.domain.valueObjects.TunableItem.TunableItemType;

public interface TuneetRepository {
    public PageResult<Tuneet> findAll(Pagination pagination);
    public PageResult<Tuneet> findByAuthorId(String authorId, Pagination pagination);
    public PageResult<Tuneet> findByTunableItemId(String tunableItemId, Pagination pagination);
    public PageResult<Tuneet> findByTunableItemTitleContaining(String word, Pagination pagination);
    public PageResult<Tuneet> findByTunableItemArtistContaining(String word, Pagination pagination);
    public List<TrendingTuneResult> findTrendingTunesByType(TunableItemType type, int limit);   
    public void save(Tuneet tuneet);
    public void update(Tuneet tuneet);
    public void deleteById(UUID id);
    public Optional<Tuneet> findById(UUID id);
}
