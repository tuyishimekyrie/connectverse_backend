package com.connectverse.connectverse.service;
import java.util.Collections;



import com.connectverse.connectverse.model.User;
import com.connectverse.connectverse.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;

    public void followUser(Integer userId, Integer targetUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> targetUserOptional = userRepository.findById(targetUserId);
        if (userOptional.isPresent() && targetUserOptional.isPresent()) {
            User user = userOptional.get();
            User targetUser = targetUserOptional.get();
            user.getFollowing().add(targetUser);
            targetUser.getFollowers().add(user);
            userRepository.save(user);
            userRepository.save(targetUser);
        }
    }

    public void unfollowUser(Integer userId, Integer targetUserId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> targetUserOptional = userRepository.findById(targetUserId);
        if (userOptional.isPresent() && targetUserOptional.isPresent()) {
            User user = userOptional.get();
            User targetUser = targetUserOptional.get();
            user.getFollowing().remove(targetUser);
            targetUser.getFollowers().remove(user);
            userRepository.save(user);
            userRepository.save(targetUser);
        }
    }

    public List<User> getFollowers(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return new ArrayList<>(userOptional.get().getFollowers());
        } else {
            // Return an empty list if the user is not found
            return Collections.emptyList();
        }
    }


    public int getFollowingCount(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getFollowing().size();
        } else {
            // Return 0 if the user is not found
            return 0;
        }
    }

    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }
    // Add this method
    public int getFollowerCount(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getFollowers().size();
        } else {
            // Return 0 if the user is not found
            return 0;
        }
    }
}
