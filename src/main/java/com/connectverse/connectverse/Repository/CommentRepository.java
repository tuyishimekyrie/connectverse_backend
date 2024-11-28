package com.connectverse.connectverse.Repository;

import com.connectverse.connectverse.model.Comment;
import com.connectverse.connectverse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByUser(User user);
}
