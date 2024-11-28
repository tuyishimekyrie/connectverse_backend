package com.connectverse.connectverse.controller;
import com.connectverse.connectverse.model.User;
import com.connectverse.connectverse.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowersController {
    @Autowired
    private final FollowService followService;
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Integer userId) {
        List<User> followers = followService.getFollowers(userId);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }
    // Add this endpoint
    @GetMapping("/{userId}/follower-count")
    public ResponseEntity<Map<String, Integer>> getFollowerCount(@PathVariable Integer userId) {
        int followerCount = followService.getFollowerCount(userId);
        Map<String, Integer> response = new HashMap<>();
        response.put("count", followerCount);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{userId}/following-count")
    public ResponseEntity<Map<String, Integer>> getFollowingCount(@PathVariable Integer userId) {
        int followingCount = followService.getFollowingCount(userId);
        Map<String, Integer> response = new HashMap<>();
        response.put("count", followingCount);
        return ResponseEntity.ok(response);
    }

}
