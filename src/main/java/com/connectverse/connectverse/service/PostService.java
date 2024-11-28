package com.connectverse.connectverse.service;

import com.connectverse.connectverse.Dto.PostDto;
import com.connectverse.connectverse.Repository.PostRepository;
import com.connectverse.connectverse.model.Post;
import com.connectverse.connectverse.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public String addPost(Post post) {
        try {
            postRepository.save(post);
            return "Post saved successfully";
        } catch (Exception e) {
            return "Failed to save post: " + e.getMessage();
        }
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
