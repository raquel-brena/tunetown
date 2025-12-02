package com.imd.backend.app.service;

import com.imd.backend.domain.entities.core.Follow;
import com.imd.backend.domain.entities.core.Profile;
import com.imd.backend.infra.persistence.jpa.repository.FollowRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final ProfileService profileService;

    public void follow(String idFollower, String idFollowed) {

        Profile follower = this.profileService.findEntityById(idFollower);
        Profile followed = this.profileService.findEntityById(idFollowed);

        boolean alreadyFollowing = followRepository.existsByFollowerAndFollowed(follower, followed);
        if (alreadyFollowing) return;

        Follow follow = Follow.builder()
                .follower(follower)
                .followed(followed)
                .createdAt(LocalDateTime.now())
                .build();

        followRepository.save(follow);
    }

    public void unfollow(String idFollower, String idFollowed) {
        Profile follower = this.profileService.findEntityById(idFollower);
        Profile followed = this.profileService.findEntityById(idFollowed);

        followRepository.deleteByFollowerAndFollowed(follower, followed);
    }

    public boolean isFriend(String idFollower, String idFollowed) {
        Profile follower = this.profileService.findEntityById(idFollower);
        Profile followed = this.profileService.findEntityById(idFollowed);

        return followRepository.existsByFollowerAndFollowed(follower, followed) &&
                followRepository.existsByFollowerAndFollowed(followed, follower);
    }

    public List<Profile> getFriends(String idProfile) {
        Profile profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollower(profile)
                .stream()
                .map(Follow::getFollowed)
                .filter(followed -> followRepository.existsByFollowerAndFollowed(followed, profile))
                .toList();
    }

    public List<Profile> getFollowing(String idProfile) {
        Profile profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollower(profile)
                .stream()
                .map(Follow::getFollowed)
                .toList();
    }

    public List<Profile> getFollowers(String idProfile) {
        Profile profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollowed(profile)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }
}
