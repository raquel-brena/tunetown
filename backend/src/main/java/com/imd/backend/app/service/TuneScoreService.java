package com.imd.backend.app.service;

import com.imd.backend.api.dto.tunescore.TuneScoreResponse;
import com.imd.backend.domain.entities.Tuneet;
import com.imd.backend.domain.valueObjects.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TuneScoreService {
    public TuneetService tuneetService;

    @Autowired
    public TuneScoreService(TuneetService tuneetService) {
        this.tuneetService = tuneetService;
    }

    public TuneScoreResponse calculateTuneScore(UUID firstUserId, UUID secondUserId) {
        var pagination = new Pagination(0, 50, "id", "DESC");

        var firstUserTuneets = tuneetService.findTuneetsByAuthorId(firstUserId, pagination);
        var secondUserTuneets = tuneetService.findTuneetsByAuthorId(secondUserId, pagination);

        var firstUserTunableItems = firstUserTuneets.itens().stream()
                .map(Tuneet::getTunableContent).toList();

        var secondUserTunableItems = secondUserTuneets.itens().stream()
               .map(Tuneet::getTunableContent).toList();

        return new TuneScoreResponse(
                firstUserId.toString(),
                secondUserId.toString(),
                0.0f,
                firstUserTunableItems.toString()
        );
    }
}
