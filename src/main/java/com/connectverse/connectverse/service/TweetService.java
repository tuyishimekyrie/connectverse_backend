package com.connectverse.connectverse.service;

import com.connectverse.connectverse.Dto.ProfileDto;
import com.connectverse.connectverse.Dto.TweetDto;
import com.connectverse.connectverse.Repository.TweetRepository;
import com.connectverse.connectverse.model.Post;
import com.connectverse.connectverse.model.Profile;
import com.connectverse.connectverse.model.Tweet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;

//    public List<Tweet> getAllTweets() {
//        return tweetRepository.findAll();
//    }
public List<Tweet> getAllTweets() {
    // Create a Sort object to specify sorting by creation date in descending order
    Sort sortByCreationDateDesc = Sort.by(Sort.Direction.DESC, "createdAt");

    // Use the findAll method with the Sort object to retrieve tweets ordered by creation date
    return tweetRepository.findAll(sortByCreationDateDesc);
}
    public String addTweet(Tweet tweet) {
        try {
            tweetRepository.save(tweet);
            return "tweet saved successfully";
        } catch (Exception e) {
            return "Failed to save tweet: " + e.getMessage();
        }
    }
    public TweetDto getTweetById(Integer tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        TweetDto tweetDto = convertToDto(tweet);
        return tweetDto;
    }
    private TweetDto convertToDto(Tweet tweet) {
        return TweetDto.builder()
                .id(tweet.getTweet_id())
                .message(tweet.getMessage())
                .imageUrl(tweet.getImageUrl())
                .videoUrl(tweet.getVideoUrl())
                .build();
    }
    public List<TweetDto> getAllTweetsForUser(Long userId) {
        List<Tweet> tweets = tweetRepository.findByUserId(userId);
        if (tweets.isEmpty()) {
            throw new RuntimeException("No tweets found for user with ID: " + userId);
        }
        return convertToDtoList(tweets);
    }

    private List<TweetDto> convertToDtoList(List<Tweet> tweets) {
        return tweets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteTweet(Integer tweetId) {
        tweetRepository.deleteById(tweetId);
    }
}
