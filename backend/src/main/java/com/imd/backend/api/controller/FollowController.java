package com.imd.backend.api.controller;

import com.imd.backend.api.dto.RestResponseMessage;
import com.imd.backend.app.service.FollowService;
import com.imd.backend.infra.persistence.jpa.mapper.FollowerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("api/follows")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{followerId}/follow/{followedId}")
    public ResponseEntity<RestResponseMessage> follow(
            @PathVariable UUID followerId,
            @PathVariable UUID followedId
    ) {
        followService.follow(followerId, followedId);
        return ResponseEntity.ok(new RestResponseMessage(null, HttpStatus.OK.value(), "Follow realizado com sucesso!"));
    }

    @DeleteMapping("/{followerId}/unfollow/{followedId}")
    public ResponseEntity<RestResponseMessage> unfollow(
            @PathVariable UUID followerId,
            @PathVariable UUID followedId
    ) {
        followService.unfollow(followerId, followedId);
        return ResponseEntity.ok(new RestResponseMessage(null, HttpStatus.OK.value(), "Unfollow realizado com sucesso!"));
    }

    @GetMapping("/{profileId}/following")
    public ResponseEntity<RestResponseMessage> getFollowing(@PathVariable UUID profileId) {
        var following = followService.getFollowing(profileId)
                .stream()
                .map(FollowerMapper::toDTO)
                .toList();
        return ResponseEntity.ok(new RestResponseMessage(following, HttpStatus.OK.value(), ""));
    }

    @GetMapping("/{profileId}/followers")
    public ResponseEntity<RestResponseMessage> getFollowers(@PathVariable UUID profileId) {
        var followers = followService.getFollowers(profileId)
                .stream()
                .map(FollowerMapper::toDTO)
                .toList();
        return ResponseEntity.ok(new RestResponseMessage(followers, HttpStatus.OK.value(), ""));
    }
}