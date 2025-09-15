package com.imd.backend.app.service;

import com.imd.backend.domain.entities.Profile;
import com.imd.backend.domain.repository.FollowRepository;
import com.imd.backend.infra.persistence.jpa.entity.Follow;
import com.imd.backend.infra.persistence.jpa.entity.ProfileEntity;
import com.imd.backend.infra.persistence.jpa.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final ProfileService profileService;

    public void follow(String idFollower, String idFollowed) {

        ProfileEntity follower = this.profileService.findEntityById(idFollower);
        ProfileEntity followed = this.profileService.findEntityById(idFollowed);

        boolean alreadyFollowing = followRepository.existsByFollowerAndFollowed(follower, followed);
        if (alreadyFollowing) return;

        Follow follow = Follow.builder()
                .follower(follower)
                .followed(followed)
                .createdAt(new Date())
                .build();

        followRepository.save(follow);
    }

    public void unfollow(String idFollower, String idFollowed) {
        ProfileEntity follower = this.profileService.findEntityById(idFollower);
        ProfileEntity followed = this.profileService.findEntityById(idFollowed);

        followRepository.deleteByFollowerAndFollowed(follower, followed);
    }

    public boolean isFriend(String idFollower, String idFollowed) {
        ProfileEntity follower = this.profileService.findEntityById(idFollower);
        ProfileEntity followed = this.profileService.findEntityById(idFollowed);

        return followRepository.existsByFollowerAndFollowed(follower, followed) &&
                followRepository.existsByFollowerAndFollowed(followed, follower);
    }

    public List<Profile> getFriends(String idProfile) {
        ProfileEntity profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollower(profile)
                .stream()
                .filter(f -> followRepository.existsByFollowerAndFollowed(f.getFollowed(), profile))
                .map(f -> ProfileMapper.toDomain(f.getFollowed()))
                .toList();
    }

    public List<ProfileEntity> getFollowing(String idProfile) {
        ProfileEntity profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollower(profile)
                .stream()
                .map(Follow::getFollowed)
                .toList();
    }

    public List<ProfileEntity> getFollowers(String idProfile) {
        ProfileEntity profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollowed(profile)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }
}
