package com.connectverse.connectverse.controller;

import com.connectverse.connectverse.Dto.PostDto;
import com.connectverse.connectverse.Dto.ProfileDto;
import com.connectverse.connectverse.config.JwtService;
import com.connectverse.connectverse.model.Post;
import com.connectverse.connectverse.model.Profile;
import com.connectverse.connectverse.model.User;
import com.connectverse.connectverse.service.PostService;
import com.connectverse.connectverse.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor

public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Autowired
    private final JwtService jwtService;
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addPosts(@RequestHeader("Authorization") String token, @RequestBody PostDto postDto) {
        try {
            // Get the logged-in user's username from the token
            String username = getUsernameFromToken(token);

            // Retrieve the user from the database using the username
            Optional<User> userOptional = userService.getUserByEmail(username);

            // Check if the user exists
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Convert PostDto to Post entity
                Post post = convertToPostEntity(postDto);

                // Set the user for the post
                post.setUser(user);

                // Save the post entity to the database
                String result = postService.addPost(post);

                // Return a success response
                return ResponseEntity.ok(result);
            } else {
                // Handle the case where the user does not exist
                return ResponseEntity.ok("User Not Found");
            }
        } catch (Exception e) {
            // Log the exception stack trace
            e.printStackTrace();
            // Return an error response with a meaningful message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add post: " + e.getMessage());
        }
    }

    private Post convertToPostEntity(PostDto postDto) {
        return Post.builder()
                .content(postDto.getContent())
                .category(postDto.getCategory())
                .headline(postDto.getHeadline())
                .createdAt(postDto.getCreatedAt())
                .updatedAt(postDto.getUpdatedAt())
                .build();
    }

    private String getUsernameFromToken(String token) {
        // Logic to extract username from JWT token
        String jwt = token.substring(7);
        return jwtService.extractUsername(jwt);
    }

}
