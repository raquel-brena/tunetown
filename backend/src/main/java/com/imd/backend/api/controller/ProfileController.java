package com.imd.backend.api.controller;

import com.imd.backend.api.dto.ProfileResponseDTO;
import com.imd.backend.app.service.ProfileService;
import com.imd.backend.domain.entities.Profile;
import com.imd.backend.infra.persistence.jpa.mapper.ProfileMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getById(@PathVariable String id) {
        Profile profile = profileService.findById(id);
        return ResponseEntity.ok(ProfileMapper.toDTO(profile));
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDTO> updateProfile(@Validated @RequestBody ProfileResponseDTO dto) {
        Profile updated = profileService.update(ProfileMapper.toDomain(dto));
        return ResponseEntity.ok(ProfileMapper.toDTO(updated));
    }

    @GetMapping
    public ResponseEntity<Page<ProfileResponseDTO>> findAll(Pageable pageable) {
        Page<Profile> profiles = profileService.findAll(pageable);
        Page<ProfileResponseDTO> dtoPage = profiles.map(ProfileMapper::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> create(@Validated @RequestBody ProfileResponseDTO dto) {
        Profile created = profileService.create(ProfileMapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProfileMapper.toDTO(created));
    }
}
