package com.connectverse.connectverse.service;

import com.connectverse.connectverse.model.Comment;
import com.connectverse.connectverse.model.Tweet;
import com.connectverse.connectverse.model.User;
import com.connectverse.connectverse.Repository.CommentRepository;
import com.connectverse.connectverse.Repository.TweetRepository;
import com.connectverse.connectverse.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final TweetRepository tweetRepository;
    @Autowired
    private final UserRepository userRepository;

    public void addComment(Integer tweetId, Integer userId, String content) throws Exception {
        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (tweetOptional.isPresent() && userOptional.isPresent()) {
            Tweet tweet = tweetOptional.get();
            User user = userOptional.get();

            Comment comment = new Comment();
            comment.setTweet(tweet);
            comment.setUser(user);
            comment.setContent(content);
            commentRepository.save(comment);
        } else {
            throw new Exception("Tweet or User not found");
        }
    }
    public List<Comment> getAllCommentsForTweet(Integer tweetId) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
        if (tweetOptional.isPresent()) {
            return tweetOptional.get().getComments();
        } else {
            return List.of(); // Return an empty list if the tweet is not found
        }
    }

    public List<Comment> getAllCommentsByUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return commentRepository.findByUser(user);
        } else {
            return List.of(); // Return an empty list if the user is not found
        }
    }
}
