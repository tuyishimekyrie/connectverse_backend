package com.connectverse.connectverse.Repository;

import com.connectverse.connectverse.model.Like;
import com.connectverse.connectverse.model.Tweet;
import com.connectverse.connectverse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByTweetAndUser(Tweet tweet, User user);
}
