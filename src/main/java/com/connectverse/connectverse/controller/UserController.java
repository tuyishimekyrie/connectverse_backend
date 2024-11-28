package com.connectverse.connectverse.controller;

import com.connectverse.connectverse.Dto.EmailDto;
import com.connectverse.connectverse.Dto.PostDto;
import com.connectverse.connectverse.config.JwtService;
import com.connectverse.connectverse.model.Post;
import com.connectverse.connectverse.service.FollowService;
import com.connectverse.connectverse.service.UserService;
import com.connectverse.connectverse.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
//        List<ResponseEntity<User>> responseEntities = users.stream()
//                .map(user -> ResponseEntity.ok(user))
//                .collect(Collectors.toList());
        return users;
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }


    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Welcome user");
    }

    @PostMapping(value = "/getuserbyemail", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByEmail(@RequestBody EmailDto email) {
        Optional<User> result = userService.getUserByEmail(email.getEmail());
        System.out.println("Result" + result);
        if (!result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed To Retrive the user");
        }
    }
    @PostMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<String> followUser(@PathVariable Integer userId, @PathVariable Integer targetUserId) {
        followService.followUser(userId, targetUserId);
        return ResponseEntity.ok("Followed user successfully");
    }

    @PostMapping("/{userId}/unfollow/{targetUserId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Integer userId, @PathVariable Integer targetUserId) {
        followService.unfollowUser(userId, targetUserId);
        return ResponseEntity.ok("Unfollowed user successfully");
    }



    private String getUsernameFromToken(String token) {
        // Logic to extract username from JWT token
        String jwt = token.substring(7);
        return jwtService.extractUsername(jwt);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByUsername(@RequestParam String username) {
        List<User> users = followService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

}
