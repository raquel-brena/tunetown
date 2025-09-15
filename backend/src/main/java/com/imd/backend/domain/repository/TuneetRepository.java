package com.imd.backend.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TuneetResume;

public interface TuneetRepository {
    public void save(Tuneet tuneet);
    public void deleteById(UUID id);
    public Optional<TuneetResume> findById(UUID id);
}
