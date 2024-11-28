package com.connectverse.connectverse.service;

import com.connectverse.connectverse.model.Like;
import com.connectverse.connectverse.model.Tweet;
import com.connectverse.connectverse.model.User;
import com.connectverse.connectverse.Repository.LikesRepository;
import com.connectverse.connectverse.Repository.TweetRepository;
import com.connectverse.connectverse.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    @Autowired
    private LikesRepository likeRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    public void likeTweet(Integer tweetId, Integer userId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new RuntimeException("Tweet not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Like> existingLike = likeRepository.findByTweetAndUser(tweet, user);
        if (existingLike.isEmpty()) {
            Like like = new Like();
            like.setTweet(tweet);
            like.setUser(user);
            likeRepository.save(like);

            tweet.setLikesCount(tweet.getLikesCount() + 1);
            tweetRepository.save(tweet);
        }
    }

    public void unlikeTweet(Integer tweetId, Integer userId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new RuntimeException("Tweet not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Like> existingLike = likeRepository.findByTweetAndUser(tweet, user);
        existingLike.ifPresent(like -> {
            likeRepository.delete(like);
            tweet.setLikesCount(tweet.getLikesCount() - 1);
            tweetRepository.save(tweet);
        });
    }


//    public void toggleLikeTweet(Integer tweetId, Integer userId) throws Exception {
//        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
//        Optional<User> userOptional = userRepository.findById(userId);
//
//        if (tweetOptional.isPresent() && userOptional.isPresent()) {
//            Tweet tweet = tweetOptional.get();
//            User user = userOptional.get();
//
//            // Check if the user has already liked the tweet
//            Optional<Like> existingLike = likeRepository.findByTweetAndUser(tweet, user);
//            if (existingLike.isPresent()) {
//                // User has already liked the tweet, so remove the like
//                likeRepository.delete(existingLike.get());
//                tweet.setLikesCount(tweet.getLikesCount() - 1);
//            } else {
//                // User has not liked the tweet, so add a new like
//                Like like = new Like();
//                like.setTweet(tweet);
//                like.setUser(user);
//                likeRepository.save(like);
//                tweet.setLikesCount(tweet.getLikesCount() + 1);
//            }
//            tweetRepository.save(tweet);
//        } else {
//            throw new Exception("Tweet or User not found");
//        }
//    }
public void toggleLikeTweet(Integer tweetId, Integer userId) throws Exception {
    Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);
    Optional<User> userOptional = userRepository.findById(userId);

    if (tweetOptional.isPresent() && userOptional.isPresent()) {
        Tweet tweet = tweetOptional.get();
        User user = userOptional.get();

        // Check if the user has already liked the tweet
        Optional<Like> existingLike = likeRepository.findByTweetAndUser(tweet, user);
        if (existingLike.isPresent()) {
            // User has already liked the tweet, so remove the like
            likeRepository.delete(existingLike.get());
            tweet.setLikesCount(tweet.getLikesCount() - 1);
        } else {
            // User has not liked the tweet, so add a new like
            Like like = new Like();
            like.setTweet(tweet);
            like.setUser(user);
            likeRepository.save(like);
            tweet.setLikesCount(tweet.getLikesCount() + 1);
        }
        tweetRepository.save(tweet);
    } else {
        throw new Exception("Tweet or User not found");
    }
}
}
