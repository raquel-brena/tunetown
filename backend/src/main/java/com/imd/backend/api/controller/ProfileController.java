package com.imd.backend.api.controller;

import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.api.dto.profile.ProfileCreateDTO;
import com.imd.backend.api.dto.profile.ProfileResponseDTO;
import com.imd.backend.api.dto.profile.ProfileUpdateDTO;
import com.imd.backend.app.service.ProfileService;
import com.imd.backend.domain.entities.Profile;
import com.imd.backend.domain.exception.NotFoundException;
import com.imd.backend.infra.persistence.jpa.mapper.ProfileMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<Page<ProfileResponseDTO>> findAll(Pageable pageable) {
        Page<Profile> profiles = profileService.findAll(pageable);
        Page<ProfileResponseDTO> dtoPage = profiles.map(ProfileMapper::toDTO);
        if (!dtoPage.hasContent()) {
            throw new NotFoundException("Nenhum profile encontrado.");
        }
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponseMessage> findById(@PathVariable String id) {
        Profile profile = profileService.findById(id);
        return ResponseEntity.ok(new RestResponseMessage(ProfileMapper.toDTO(profile), HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<RestResponseMessage> create(@Valid @RequestBody ProfileCreateDTO dto) {
        Profile profile = profileService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponseMessage(ProfileMapper.toDTO(profile), HttpStatus.CREATED.value()));
    }

    @PutMapping
    public ResponseEntity<RestResponseMessage> update(@RequestBody ProfileUpdateDTO dto) {
        Profile profile = profileService.update(ProfileMapper.toDomain(dto));
        return ResponseEntity.ok(new RestResponseMessage(ProfileMapper.toDTO(profile), HttpStatus.OK.value(), "Profile updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        profileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<RestResponseMessage> updatePhoto(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Profile updated = profileService.updatePhoto(id, file);
        return ResponseEntity.ok(new RestResponseMessage(ProfileMapper.toDTO(updated), HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}/photo")
    public ResponseEntity<RestResponseMessage> deletePhoto(
            @PathVariable String id
    ) throws IOException {
        Profile updated = profileService.deletePhoto(id);
        return ResponseEntity.ok(new RestResponseMessage(ProfileMapper.toDTO(updated), HttpStatus.OK.value()));
    }
}
