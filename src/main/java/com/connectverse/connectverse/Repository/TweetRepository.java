package com.connectverse.connectverse.Repository;

import com.connectverse.connectverse.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    List<Tweet> findByUserId(Long userId);
}
