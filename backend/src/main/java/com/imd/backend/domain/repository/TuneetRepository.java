package com.imd.backend.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.imd.backend.domain.entities.Tuneet;

public interface TuneetRepository {
    public void save(Tuneet tuneet);
    public void update(Tuneet tuneet);
    public void deleteById(UUID id);
    public Optional<Tuneet> findById(UUID id);
}
