package com.imd.backend.api.controller;

import com.imd.backend.api.controller.core.BasePostController;
import com.imd.backend.app.dto.tunetown.CreateTuneetDTO;
import com.imd.backend.app.dto.tunetown.UpdateTuneetDTO;
import com.imd.backend.app.service.TuneetService;
import com.imd.backend.domain.entities.tunetown.Tuneet;
import com.imd.backend.domain.valueobjects.tunableitem.TunableItem;
import com.imd.backend.infra.security.CoreUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/tuneet")
public class TuneetController extends BasePostController<
  Tuneet, 
  TunableItem,
  CreateTuneetDTO,
  UpdateTuneetDTO
> {
  public TuneetController(TuneetService tuneetService) {
    super(tuneetService);
  }

  @Override
  @PostMapping
  public ResponseEntity<Tuneet> create(
    @RequestBody @Validated CreateTuneetDTO dto,
    @AuthenticationPrincipal CoreUserDetails userDetails
  ) {
    return super.create(dto, userDetails);
  } // COLOQUEI ESSE MÉTODO PRA CÁ POIS ESTAVA DANDO PROBLEMA NO JACKSON COM O DTO GENÉRICO

  @Override
  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Tuneet> update(
    @PathVariable UUID id,
    @Validated @RequestBody UpdateTuneetDTO dto,
    @AuthenticationPrincipal CoreUserDetails userDetails 
  ) {
    return super.update(id, dto, userDetails);
  }  
}