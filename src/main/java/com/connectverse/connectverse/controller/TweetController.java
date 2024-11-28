package com.connectverse.connectverse.controller;

import com.connectverse.connectverse.Dto.CommentDto;
import com.connectverse.connectverse.Dto.PostDto;
import com.connectverse.connectverse.Dto.ProfileDto;
import com.connectverse.connectverse.Dto.TweetDto;
import com.connectverse.connectverse.config.JwtService;
import com.connectverse.connectverse.model.Comment;
import com.connectverse.connectverse.model.Post;
import com.connectverse.connectverse.model.Tweet;
import com.connectverse.connectverse.model.User;
import com.connectverse.connectverse.service.CommentService;
import com.connectverse.connectverse.service.LikeService;
import com.connectverse.connectverse.service.TweetService;
import com.connectverse.connectverse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tweet")
@RequiredArgsConstructor
public class TweetController {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/")
    public ResponseEntity<List<Tweet>> getAllTweets() {
        List<Tweet> tweets = tweetService.getAllTweets();
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addPosts(@RequestHeader("Authorization") String token, @RequestBody TweetDto tweetDto) {
        try {
            // Get the logged-in user's username from the token
            String username = getUsernameFromToken(token);

            // Retrieve the user from the database using the username
            Optional<User> userOptional = userService.getUserByEmail(username);

            // Check if the user exists
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Convert PostDto to Post entity
                Tweet tweet = convertToTweetEntity(tweetDto);

                // Set the user for the post
                tweet.setUser(user);

                // Save the post entity to the database
                String result = tweetService.addTweet(tweet);

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

    private Tweet convertToTweetEntity(TweetDto tweetDto) {
        return Tweet.builder()
                .message(tweetDto.getMessage())
                .imageUrl(tweetDto.getImageUrl())
                .videoUrl(tweetDto.getVideoUrl())
                .likesCount(tweetDto.getLikesCount())
                .build();
    }

    private String getUsernameFromToken(String token) {
        // Logic to extract username from JWT token
        String jwt = token.substring(7);
        return jwtService.extractUsername(jwt);
    }
    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetDto> getProfile(@PathVariable Integer tweetId) {
        TweetDto tweet = tweetService.getTweetById(tweetId);
        return ResponseEntity.ok(tweet);
    }

//    @PostMapping("/{tweetId}/like")
//    public ResponseEntity<String> likeTweet(@RequestHeader("Authorization") String token, @PathVariable Integer tweetId) {
//        try {
//            String username = getUsernameFromToken(token);
//            Optional<User> userOptional = userService.getUserByEmail(username);
//
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//                likeService.likeTweet(tweetId, user.getId());
//                return ResponseEntity.ok("Tweet liked successfully");
//            } else {
//                return ResponseEntity.ok("User Not Found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to like tweet: " + e.getMessage());
//        }
//    }

    @PostMapping("/{tweetId}/unlike")
    public ResponseEntity<String> unlikeTweet(@RequestHeader("Authorization") String token, @PathVariable Integer tweetId) {
        try {
            String username = getUsernameFromToken(token);
            Optional<User> userOptional = userService.getUserByEmail(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                likeService.unlikeTweet(tweetId, user.getId());
                return ResponseEntity.ok("Tweet unliked successfully");
            } else {
                return ResponseEntity.ok("User Not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unlike tweet: " + e.getMessage());
        }
    }
    @PostMapping("/{tweetId}/like")
    public ResponseEntity<String> toggleLikeTweet(@RequestHeader("Authorization") String token, @PathVariable Integer tweetId) {
        try {
            String username = getUsernameFromToken(token);
            Optional<User> userOptional = userService.getUserByEmail(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                likeService.toggleLikeTweet(tweetId, user.getId());
                return ResponseEntity.ok("Tweet like status toggled successfully");
            } else {
                return ResponseEntity.ok("User Not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to toggle like status: " + e.getMessage());
        }
    }
    @PostMapping("/{tweetId}/comment")
    public ResponseEntity<String> addComment(@RequestHeader("Authorization") String token, @PathVariable Integer tweetId, @RequestBody CommentDto commentDto) {
        try {
            String username = getUsernameFromToken(token);
            Optional<User> userOptional = userService.getUserByEmail(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Comment comment = convertToCommentEntity(commentDto);
                commentService.addComment(tweetId, user.getId(),comment.getContent());
                return ResponseEntity.ok("Comment added successfully");
            } else {
                return ResponseEntity.ok("User Not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment: " + e.getMessage());
        }
    }
    private Comment convertToCommentEntity(CommentDto commentDto) {
        return Comment.builder()
                .content(commentDto.getContent())
                .build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllTweetsForUser(@PathVariable Long userId) {
        List<TweetDto> tweets = tweetService.getAllTweetsForUser(userId);
        if (tweets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tweets found for user with ID: " + userId);
        } else {
            return ResponseEntity.ok(tweets);
        }
    }
    @GetMapping("/user/comments/{userId}")
    public ResponseEntity<List<Comment>> getAllCommentsByUser(@PathVariable Integer userId) {
        List<Comment> comments = commentService.getAllCommentsByUser(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @DeleteMapping("/{tweetId}")
    public ResponseEntity<String> deleteTweet(@PathVariable Integer tweetId) {
        tweetService.deleteTweet(tweetId);
        return ResponseEntity.ok("Tweet deleted successfully");
    }
}
