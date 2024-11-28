package com.connectverse.connectverse.Repository;


import com.connectverse.connectverse.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
