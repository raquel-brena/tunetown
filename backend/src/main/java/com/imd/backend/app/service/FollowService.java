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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final ProfileService profileService;

    public void follow(UUID idFollower, UUID idFollowed) {

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

    public void unfollow(UUID idFollower, UUID idFollowed) {
        ProfileEntity follower = this.profileService.findEntityById(idFollower);
        ProfileEntity followed = this.profileService.findEntityById(idFollowed);

        followRepository.deleteByFollowerAndFollowed(follower, followed);
    }

    public boolean isFriend(UUID idFollower, UUID idFollowed) {
        ProfileEntity follower = this.profileService.findEntityById(idFollower);
        ProfileEntity followed = this.profileService.findEntityById(idFollowed);

        return followRepository.existsByFollowerAndFollowed(follower, followed) &&
                followRepository.existsByFollowerAndFollowed(followed, follower);
    }

    public List<Profile> getFriends(UUID idProfile) {
        ProfileEntity profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollower(profile)
                .stream()
                .filter(f -> followRepository.existsByFollowerAndFollowed(f.getFollowed(), profile))
                .map(f -> ProfileMapper.toDomain(f.getFollowed()))
                .toList();
    }

    public List<ProfileEntity> getFollowing(UUID idProfile) {
        ProfileEntity profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollower(profile)
                .stream()
                .map(Follow::getFollowed)
                .toList();
    }

    public List<ProfileEntity> getFollowers(UUID idProfile) {
        ProfileEntity profile = profileService.findEntityById(idProfile);
        return followRepository.findByFollowed(profile)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }
}
