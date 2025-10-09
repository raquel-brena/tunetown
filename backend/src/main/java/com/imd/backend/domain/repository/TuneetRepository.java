package com.imd.backend.domain.repository;

import java.util.Optional;

import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.entities.TuneetResume;

public interface TuneetRepository {
    public void save(Tuneet tuneet);
    public void update(TuneetResume tuneetResume);
    public void deleteById(String id);
    public Optional<TuneetResume> findById(String id);
}
